package cn.exam.service.impl;

import cn.exam.dao.mapper.qte.QteRoleMapper;
import cn.exam.dao.mapper.qte.QteRoleMenuMapper;
import cn.exam.domain.qte.QteRole;
import cn.exam.domain.qte.QteRoleMenu;
import cn.exam.query.RoleQuery;
import cn.exam.service.RoleService;
import cn.exam.util.PageResult;
import cn.exam.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pekosmile
 * @version 1.0
 * @date ##
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private QteRoleMapper roleMapper;
    @Autowired
    private QteRoleMenuMapper roleMenuMapper;
    @Override
    public PageResult<List<QteRole>> queryPage(RoleQuery query) {
        return PageUtil.execute(()->roleMapper.queryPage(query),query);
    }

    @Override
    public void insertRoleInfo(QteRole role) {
        roleMapper.insertSelective(role);
    }

    @Override
    public void deleteRole(Integer id) {
        QteRole tblRole = roleMapper.selectByPrimaryKey(id);
        List<QteRoleMenu> list = roleMenuMapper.queryRoleMenuInfoByRoleId(tblRole.getRoleId());
        list.forEach(f-> roleMenuMapper.deleteByPrimaryKey(f.getId()));
        roleMapper.deleteByPrimaryKey(id);
    }
}
