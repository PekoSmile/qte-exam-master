package cn.exam.controller;

import cn.exam.config.BaseController;
import cn.exam.config.RedisUtil;
import cn.exam.dao.mapper.zj.ZjClassInfoMapper;
import cn.exam.dao.mapper.zj.ZjUserInfoMapper;
import cn.exam.dao.mapper.zj.ZjUserRoleMapper;
import cn.exam.domain.zj.ZjClassInfo;
import cn.exam.domain.zj.ZjUserInfo;
import cn.exam.domain.zj.ZjUserRole;
import cn.exam.query.ClassQuery;
import cn.exam.query.UserQuery;
import cn.exam.redis.RedisKeyEnum;
import cn.exam.service.UserInfoService;
import cn.exam.service.ZjClassInfoService;
import cn.exam.service.ZjRoleMenuService;
import cn.exam.service.impl.ZjClassInfoServiceImpl;
import cn.exam.util.*;
import cn.exam.vo.MenuInfoVO;
import cn.exam.vo.UserPageVO;
import cn.exam.vo.UserVO;
import cn.zq.exam.so.UserInfoSO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author pekosmile
 * @version 1.0
 * @date ##
 */
@Controller
@RequestMapping("login")
public class LoginController extends BaseController {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private ZjUserInfoMapper userInfoMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ZjRoleMenuService roleMenuService;
    @Autowired
    private ZjUserRoleMapper  userRoleMapper;

    /**
     * 登录
     */
    @RequestMapping("login.htm")
    public void login(ZjUserInfo userInfo, HttpServletResponse response) {
        ResultDTO<UserVO> resultDTO = new ResultDTO<>();
        //注册shiro
        UserVO tblUser = userInfoService.queryUserInfoByName(userInfo.getUserId());
        if (ObjectUtils.isEmpty(tblUser)) {
            throw new ExpressException(SystemCode.SERVICE_FAILD_CODE, "账号输入有误");
        }
        String s = MD5Utils.md5(userInfo.getPassword());
        if (!s.equals(tblUser.getPassword())) {
            throw new ExpressException(SystemCode.SERVICE_FAILD_CODE, "密码错误");
        }
        UsernamePasswordToken shiroToken = new UsernamePasswordToken(userInfo.getUserId(), MD5Utils.md5(userInfo.getPassword()));
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.login(shiroToken);
        //获取用户信息
        Object principal = SecurityUtils.getSubject().getPrincipal();
        UserVO user1 = (UserVO) principal;
        try {
            resultDTO.setDescription(SystemCode.RET_MSG_SUCC);
            String token = UUID.randomUUID().toString();
            user1.setToken(token);
            resultDTO.setCode(SystemCode.RET_CODE_SUCC);
            resultDTO.setResult(user1);//LOGIN_USER_INFO:UUID.randomUUID().toString()
            redisUtil.setKeyTime(RedisKeyEnum.USER.getCode() + ":" + token, JSON.toJSONString(user1), Constant.KEY_IN_REDIS_TIME);

        } catch (IncorrectCredentialsException e) {
            resultDTO.setCode(SystemCode.USER_LOGIN_ERROR_CODE);
            resultDTO.setDescription("密码错误");
        } catch (LockedAccountException e) {
            resultDTO.setCode(SystemCode.USER_LOGIN_ERROR_CODE);
            resultDTO.setDescription("登录失败，该用户已被冻结");
        } catch (IllegalStateException e) {
            resultDTO.setCode(SystemCode.SYS_OVERDUE_CODE);
            resultDTO.setDescription(SystemCode.SYS_OVERDUE_CODE_DESC);
        } catch (Exception e) {
            resultDTO.setCode(SystemCode.USER_LOGIN_ERROR_CODE);
            resultDTO.setDescription(SystemCode.USER_LOGIN_ERROR_MSG);
        }
        sendJsonResult(resultDTO, response);
    }

    /**
     * 角色菜单查询
     *
     * @param roleId   角色id
     * @param response 响应体
     */
    @RequestMapping("queryMenuList.htm")
    public void queryMenuList(String roleId, HttpServletResponse response) {
        JSONArray array = JSON.parseArray(roleId);
        List<String> roleIdList = new ArrayList<>();
        for (Object json : array) {
            JSONObject jsonObject = JSON.parseObject(json.toString());
            Object roleId1 = jsonObject.get("roleId");
            roleIdList.add(roleId1.toString());
        }
        ResultDTO<List<MenuInfoVO>> resultDTO = new ResultDTO<>();
        List<MenuInfoVO> menuVOS = roleMenuService.queryMenuList(roleIdList);
        //jdk8新特性 stream流处理去重
        ArrayList<MenuInfoVO> infos2 =
                menuVOS.stream()
                        .collect(Collectors.collectingAndThen(Collectors
                                .toCollection(() -> new TreeSet<>(Comparator
                                        .comparing(MenuInfoVO::getMenuId))), ArrayList::new));
        resultDTO.setResult(infos2);
        resultDTO.buildReturnCode(SystemCode.RET_CODE_SUCC, SystemCode.RET_MSG_SUCC);
        sendJson(resultDTO, response);
    }

    /**
     * 退出
     */
    @RequestMapping(method = RequestMethod.POST, value = "logout.htm")
    public void logout(HttpServletResponse response) {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        sendJsonSuccess(response);
    }


    /**
     * 注册
     */
    @RequestMapping("registerLogin.htm")
    public void registerLogin(UserInfoSO so, HttpServletResponse response) {
        ResultDTO<ZjUserInfo> resultDTO = new ResultDTO<>();
        if (ObjectUtils.isEmpty(so.getUserId()) || ObjectUtils.isEmpty(so.getPassword()) || ObjectUtils.isEmpty(so.getUserName())) {
            throw new ExpressException(SystemCode.SERVICE_FAILD_CODE, "必填字段不能为空");
        }
        if (!so.getPassword().equals(so.getConfirmPassword())) {
            throw new ExpressException(SystemCode.SERVICE_FAILD_CODE, "密码不一致");
        }
        UserVO user = userInfoMapper.queryShiroUserInfoByUserName(so.getUserId());
        if (!ObjectUtils.isEmpty(user)) {
            throw new ExpressException(SystemCode.SERVICE_FAILD_CODE, "账号已存在，请勿重复注册");
        }
        ZjUserInfo userInfo = new ZjUserInfo();
        String currentTime = DateUtils.getCurrentTime();
        userInfo.setUserId(so.getUserId());
        userInfo.setUserName(so.getUserName());
        userInfo.setTypeId(so.getTypeId());
        if (so.getTypeId()==0){
            userInfo.setClassId(so.getClassId());
        }
        userInfo.setPassword(MD5Utils.md5(so.getPassword()));
        userInfo.setCreateTime(currentTime);
        userInfo.setUpdateTime(currentTime);
        userInfoMapper.insertSelective(userInfo);
        ZjUserRole userRole = new ZjUserRole();
        if (so.getTypeId()==0){
            userRole.setRoleId("student");
        }else if (so.getTypeId()==1){
            userRole.setRoleId("teacher");
        }
        userRole.setUserId(userInfo.getUserId());
        userRoleMapper.insertSelective(userRole);
        resultDTO.setDescription(SystemCode.RET_MSG_SUCC);
        resultDTO.setCode(SystemCode.RET_CODE_SUCC);
        sendJsonSuccess(resultDTO, response);
    }

    /**
     * 用户查询页面
     */
    @RequestMapping("queryUserInfo.htm")
    public void queryPage(HttpServletResponse response, UserQuery query) {
        ResultDTO<PageResult<List<UserPageVO>>> resultDTO = new ResultDTO<>();
        PageResult<List<UserPageVO>> listPageResult = userInfoService.queryPage(query);
        resultDTO.setResult(listPageResult);
        resultDTO.buildReturnCode(SystemCode.RET_CODE_SUCC, SystemCode.RET_MSG_SUCC);
        sendJsonSuccessPage(resultDTO, response);
    }


    /**
     * 用户密码修改
     */
    @RequestMapping("updatePwd.htm")
    public void  updatePwd(HttpServletResponse response, UserInfoSO so){
        ZjUserInfo zjUserInfo = userInfoMapper.selectByPrimaryKey(so.getUserId());
        if (ObjectUtils.isEmpty(zjUserInfo)) {
            throw new ExpressException(SystemCode.SERVICE_FAILD_CODE, "异常用户信息");
        }
        ZjUserInfo userInfo = new ZjUserInfo();
        if (MD5Utils.md5(so.getPassword()).equals(zjUserInfo.getPassword())) {
            String s = MD5Utils.md5(so.getConfirmPassword());
            userInfo.setUserId(so.getUserId());
            userInfo.setPassword(s);
            userInfoMapper.updateByPrimaryKeySelective(userInfo);
            sendJsonSuccess(response);
        }else {
            sendJsonError(SystemCode.SERVICE_FAILD_CODE,"原密码错误",response);
        }
    }


    /**
     * 用户信息修改
     */
    @RequestMapping("updateUserInfo.htm")
    public void updateUserInfo(HttpServletResponse response, UserInfoSO so) {
        ZjUserInfo zjUserInfo = userInfoMapper.selectByPrimaryKey(so.getUserId());
        if (ObjectUtils.isEmpty(zjUserInfo)) {
            throw new ExpressException(SystemCode.SERVICE_FAILD_CODE, "用户信息异常");
        }
        ZjUserInfo userInfo = new ZjUserInfo();
        //如果密码修改，则和原密码对比  不一致新密码重新md5存入数据库
        if (!MD5Utils.md5(so.getPassword()).equals(zjUserInfo.getPassword())) {
            String s = MD5Utils.md5(so.getPassword());
            userInfo.setPassword(s);
        }
        userInfo.setTypeId(Integer.valueOf(so.getTypeId()));
        userInfo.setIsDelete(Integer.valueOf(so.getIsDelete()));
        userInfo.setUserId(so.getUserId());
        userInfo.setClassId(so.getClassId());
        userInfo.setUserName(so.getUserName());
        userInfo.setUpdateTime(DateUtils.getCurrentTime());
        userInfoMapper.updateByPrimaryKeySelective(userInfo);
        sendJsonSuccess(response);
    }

}
