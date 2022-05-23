package cn.exam.service.impl;

import cn.exam.dao.mapper.qte.QteMenuInfoMapper;
import cn.exam.dao.mapper.qte.QteRoleMenuMapper;
import cn.exam.domain.qte.QteMenuInfo;
import cn.exam.domain.qte.QteRoleMenu;
import cn.exam.query.MenuQuery;
import cn.exam.service.MenuInfoService;
import cn.exam.so.RoleMenuIdSO;
import cn.exam.util.*;
import cn.exam.vo.MenuInfoVO;
import cn.exam.vo.RoleMenuVO;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author pekosmile
 * @version 1.0
 * @date ##
 */
@Service
public class MenuInfoServiceImpl implements MenuInfoService {

    @Autowired
    private QteMenuInfoMapper menuInfoMapper;

    @Autowired
    private QteRoleMenuMapper roleMenuMapper;

    @Override
    public PageResult<List<QteMenuInfo>> queryMenuInfoPage(MenuQuery query) {
        return PageUtil.execute(() -> menuInfoMapper.queryPage(query), query);
    }

    @Override
    public List<Integer> queryMenuIdListByRoleId(String roleId) {
        List<Integer> integers = menuInfoMapper.queryMenuIdListByRoleId(roleId);
        return integers.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Override
    public List<RoleMenuVO> queryMenuTreeByRoleId(String roleId) {
        List<RoleMenuVO> roleMenuList = new ArrayList<>();
        List<QteMenuInfo> ksMenuInfos = menuInfoMapper.selectAll();
        List<QteMenuInfo> collect = ksMenuInfos.stream().filter(f -> f.getMenuStatus().equals(1)).collect(Collectors.toList());
        List<QteMenuInfo> Menu0 = collect.stream().filter(q -> q.getMenuDegree().equals(0)).collect(Collectors.toList());
        List<QteMenuInfo> Menu1 = collect.stream().filter(q -> q.getMenuDegree().equals(1)).collect(Collectors.toList());
        List<String> list = new ArrayList<>();
        list.add(roleId);
        Map<Integer, String> menuMap = new HashMap<>();
        List<MenuInfoVO> menuInfoVOS = roleMenuMapper.queryMenuList(list);
        menuInfoVOS.forEach(f -> menuMap.put(f.getMenuId(), null));
        for (QteMenuInfo info : Menu0) {
            RoleMenuVO menuVO = new RoleMenuVO();
            menuVO.setId(info.getMenuId());
            menuVO.setLabel(info.getMenuName());
            if (menuMap.containsKey(info.getMenuId())) {
                menuVO.setStatus(1);
            } else {
                menuVO.setStatus(0);
            }
            List<RoleMenuVO> roleMenu = new ArrayList<>();
            List<QteMenuInfo> collect1 = Menu1.stream().filter(q -> q.getParentId().equals(info.getMenuId())).collect(Collectors.toList());
            if (!ObjectUtils.isEmpty(collect1)) {
                for (QteMenuInfo menuInfo : collect1) {
                    RoleMenuVO children = new RoleMenuVO();
                    children.setId(menuInfo.getMenuId());
                    children.setLabel(menuInfo.getMenuName());
                    if (menuMap.containsKey(menuInfo.getMenuId())) {
                        children.setStatus(1);
                    } else {
                        children.setStatus(0);
                    }
                    roleMenu.add(children);
                }
            }
            menuVO.setChildren(roleMenu);

            roleMenuList.add(menuVO);
        }
        return roleMenuList;
    }

    @Override
    public void updateRoleMenuInfo(RoleMenuIdSO so) {
        if (ObjectUtils.isEmpty(so.getRoleId())) {
            throw new ExpressException(SystemCode.SERVICE_FAILD_CODE, "角色id为空");
        }
        List<QteRoleMenu> ksRoleMenus = roleMenuMapper.queryRoleMenuInfoByRoleId(so.getRoleId());
        ksRoleMenus.forEach(f -> roleMenuMapper.deleteByPrimaryKey(f.getId()));
        String currentDateTime = DateUtil.getCurrentDateTime();
        List<QteRoleMenu> list = new ArrayList<>();
        JSONArray objects = JSONArray.parseArray(so.getMenuIdString());
        for (Object s : objects) {
            if (!ObjectUtils.isEmpty(s)){
                QteRoleMenu menu = new QteRoleMenu();
                menu.setMenuId(Integer.valueOf(s.toString()));
                menu.setRoleId(so.getRoleId());
                menu.setCreateTime(currentDateTime);
                menu.setUpdateTime(currentDateTime);
                list.add(menu);
            }
        }
        if (!ObjectUtils.isEmpty(list)){
            roleMenuMapper.insertList(list);
        }

    }

    @Override
    public void insertMenuInfo(QteMenuInfo menuInfo) {
        menuInfoMapper.insertSelective(menuInfo);
    }

}
