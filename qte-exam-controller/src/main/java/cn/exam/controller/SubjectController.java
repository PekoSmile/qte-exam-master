package cn.exam.controller;

import cn.exam.config.BaseController;
import cn.exam.domain.qte.QteSubjectInfo;
import cn.exam.query.SubjectQuery;
import cn.exam.service.SubjectInfoService;
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
@RequestMapping("sub")
public class SubjectController extends BaseController {

    @Autowired
    private SubjectInfoService subjectInfoService;

    @RequestMapping("queryPageBySubject.htm")
    public void queryPageBySubject(HttpServletResponse response, SubjectQuery query) {
        ResultDTO<PageResult<List<QteSubjectInfo>>> resultDTO = new ResultDTO<>();
        PageResult<List<QteSubjectInfo>> listPageResult = subjectInfoService.queryPageBySubject(query);
        resultDTO.setResult(listPageResult);
        resultDTO.buildReturnCode(SystemCode.RET_CODE_SUCC, SystemCode.RET_MSG_SUCC);
        sendJsonSuccessPage(resultDTO, response);
    }


    @RequestMapping("updateBySubject.htm")
    public void updateBySubject(HttpServletResponse response, QteSubjectInfo info) {
        subjectInfoService.updateSubject(info);
        sendJsonSuccess(response);
    }

    @RequestMapping("deleteBySubject.htm")
    public void deleteBySubject(HttpServletResponse response, QteSubjectInfo info) {
        subjectInfoService.delete(info);
        sendJsonSuccess(response);
    }

    @RequestMapping("insertBySubject.htm")
    public void insertBySubject(HttpServletResponse response, QteSubjectInfo info) {
        subjectInfoService.insertSubject(info);
        sendJsonSuccess(response);
    }

    /**
     * 科目下拉选
     * @param response
     */
    @RequestMapping("queryListBySub.htm")
    public void queryListBySub(HttpServletResponse response){
        ResultDTO<List<QteSubjectInfo>> resultDTO = new ResultDTO<>();
        resultDTO.setResult( subjectInfoService.queryList());
        resultDTO.buildReturnCode(SystemCode.RET_CODE_SUCC, SystemCode.RET_MSG_SUCC);
        sendJsonSuccess(resultDTO, response);
    }
}
