package cn.exam.controller;

import cn.exam.config.BaseController;
import cn.exam.dao.mapper.qte.QteClassInfoMapper;
import cn.exam.domain.qte.QteClassInfo;
import cn.exam.query.ClassQuery;
import cn.exam.service.ClassInfoService;
import cn.exam.util.PageResult;
import cn.exam.util.ResultDTO;
import cn.exam.util.SystemCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @version 1.0
 * @date ##
 */
@Controller
@RequestMapping("class")
public class QteClassController extends BaseController {
    @Autowired
    private ClassInfoService classInfoService;
    @Autowired
    private QteClassInfoMapper classInfoMapper;


    @RequestMapping("queryPageByClass.htm")
    public void queryPageByClass(ClassQuery query , HttpServletResponse response){
        ResultDTO<PageResult<List<QteClassInfo>>> resultDTO = new ResultDTO<>();
        PageResult<List<QteClassInfo>> listPageResult = classInfoService.queryPage(query);
        resultDTO.setResult(listPageResult);
        resultDTO.buildReturnCode(SystemCode.RET_CODE_SUCC, SystemCode.RET_MSG_SUCC);
        sendJsonSuccessPage(resultDTO, response);
    }

    @RequestMapping("queryList.htm")
    public void queryList(HttpServletResponse response){
        ResultDTO<List<QteClassInfo>> resultDTO = new ResultDTO<>();
        resultDTO.setResult( classInfoMapper.selectAll());
        resultDTO.buildReturnCode(SystemCode.RET_CODE_SUCC, SystemCode.RET_MSG_SUCC);
        sendJsonSuccess(resultDTO, response);
    }


    @RequestMapping("insert.htm")
    public void insert(QteClassInfo classInfo, HttpServletResponse response){
        classInfoService.insert(classInfo);
        sendJsonSuccess(response);
    }


    @RequestMapping("delete.htm")
    public void delete(Integer id , HttpServletResponse response){
        classInfoService.delete(id);
        sendJsonSuccess(response);
    }









}

