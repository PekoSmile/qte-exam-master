package cn.exam.controller;

import cn.exam.config.BaseController;
import cn.exam.domain.qte.QteRole;
import cn.exam.query.RoleQuery;
import cn.exam.service.RoleService;
import cn.exam.util.PageResult;
import cn.exam.util.ResultDTO;
import cn.exam.util.SystemCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author pekosmile
 * @version 1.0
 * @date ##
 */
@Controller
@RequestMapping("role")
public class RoleController extends BaseController {
    @Autowired
    private RoleService roleService;

    /**
     * 分页
     * @param response 响应体
     * @param query  角色查询对象
     */
    @RequestMapping("rolePage.htm")
    public void rolePage(HttpServletResponse response , RoleQuery query){
        ResultDTO<PageResult<List<QteRole>>> resultDTO = new ResultDTO<>();
        PageResult<List<QteRole>> listPageResult = roleService.queryPage(query);
        resultDTO.setResult(listPageResult);
        resultDTO.buildReturnCode(SystemCode.RET_CODE_SUCC,SystemCode.RET_MSG_SUCC);
        sendJsonSuccessPage(resultDTO,response);
    }


    @RequestMapping("insertRoleInfo.htm")
    public void insertRoleInfo(QteRole role , HttpServletResponse response){
        roleService.insertRoleInfo(role);
        sendJsonSuccess(response);
    }


    @RequestMapping("deleteRole.htm")
    public void deleteRole(Integer id,HttpServletResponse response){
        roleService.deleteRole(id);
        sendJsonSuccess(response);
    }
}
