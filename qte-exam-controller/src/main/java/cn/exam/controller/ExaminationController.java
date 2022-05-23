package cn.exam.controller;

import cn.exam.config.BaseController;
import cn.exam.config.UserUtil;
import cn.exam.domain.qte.*;
import cn.exam.query.PaperByUserEndQuery;
import cn.exam.query.PaperQuery;
import cn.exam.query.TitlePageQuery;
import cn.exam.service.ExaminationService;
import cn.exam.util.PageResult;
import cn.exam.util.ResultDTO;
import cn.exam.util.SystemCode;
import cn.exam.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @version 1.0
 * @date ##
 */
@Controller
@RequestMapping("title")
public class ExaminationController extends BaseController {

    @Autowired
    private ExaminationService examinationService;
    @Autowired
    private UserUtil userUtil;

    @RequestMapping("queryTitlePage.htm")
    public void queryTitlePage(HttpServletResponse response, TitlePageQuery query) {
        ResultDTO<PageResult<List<TitleVO>>> resultDTO = new ResultDTO<>();
        PageResult<List<TitleVO>> listPageResult = examinationService.queryPage(query);
        resultDTO.setResult(listPageResult);
        resultDTO.buildReturnCode(SystemCode.RET_CODE_SUCC, SystemCode.RET_MSG_SUCC);
        sendJsonSuccessPage(resultDTO, response);
    }
    //试卷分页
    @RequestMapping("queryPaperPage.htm")
    public void queryTitlePage(HttpServletResponse response, PaperQuery query) {
        ResultDTO<PageResult<List<PaperPageVO>>> resultDTO = new ResultDTO<>();
        PageResult<List<PaperPageVO>> listPageResult = examinationService.queryManagerPage(query);
        resultDTO.setResult(listPageResult);
        resultDTO.buildReturnCode(SystemCode.RET_CODE_SUCC, SystemCode.RET_MSG_SUCC);
        sendJsonSuccessPage(resultDTO, response);
    }


    @RequestMapping("insertByTitle.htm")
    public void insertByTitle(QteTitleInfo info, HttpServletResponse response) {
        UserVO user = userUtil.getUser();
        examinationService.insertTitle(info, user);
        sendJsonSuccess(response);
    }

    @RequestMapping("queryTitleInfo.htm")
    public void queryTitleInfo(HttpServletResponse response, Integer titleId) {
        ResultDTO<TitleVO> resultDTO = new ResultDTO<>();
        resultDTO.setResult(examinationService.queryTitleInfo(titleId));
        resultDTO.buildReturnCode(SystemCode.RET_CODE_SUCC, SystemCode.RET_MSG_SUCC);
        sendJsonSuccess(resultDTO, response);
    }


    @RequestMapping("deleteTileInfo.htm")
    public void deleteTileInfo(HttpServletResponse response, Integer titleId) {
        examinationService.deleteTitle(titleId);
        sendJsonSuccess(response);
    }


    @RequestMapping("updateTitle.htm")
    public void updateTitle(HttpServletResponse response, QteTitleInfo info){
        examinationService.updateTitle(info);
        sendJsonSuccess(response);
    }

    //试卷页面
    @RequestMapping("queryPaper.htm")
    public void queryPaper(Integer paperId,HttpServletResponse response){
        ResultDTO<PaperTestLevel> resultDTO = new ResultDTO<>();
        resultDTO.setResult(examinationService.queryPaper(paperId));
        resultDTO.buildReturnCode(SystemCode.RET_CODE_SUCC, SystemCode.RET_MSG_SUCC);
        sendJsonSuccess(resultDTO, response);
    }
    //考生查看考完的试卷
    @RequestMapping("queryPaperCompleted.htm")
    public void   queryPaperCompleted(Integer paperId,String userId,HttpServletResponse response) {
        ResultDTO<PaperTestLevel> resultDTO = new ResultDTO<>();
        resultDTO.setResult(examinationService.queryPaperCompleted(paperId,userId));
        resultDTO.buildReturnCode(SystemCode.RET_CODE_SUCC, SystemCode.RET_MSG_SUCC);
        sendJsonSuccess(resultDTO, response);
    }

    //组卷功能
    @RequestMapping("autoExam.htm")
    public void autoExam(QtePaperInfo paperInfo, HttpServletResponse response){
        UserVO user = userUtil.getUser();
        paperInfo.setUserId(user.getUserId());
        paperInfo.setUserName(user.getUserName());
        examinationService.autoPaper(paperInfo);
        sendJsonSuccess(response);
    }

    @RequestMapping("updateTitleByList.htm")
    public void updateTitleByList(String titleString,HttpServletResponse response){
        examinationService.updateTitle(titleString);
        sendJsonSuccess(response);
    }

    /**
     * 学生已考试卷查询分页
     */
    @RequestMapping("queryPaperByUserEnd.htm")
    public void queryPaperByUserEnd(PaperByUserEndQuery query, HttpServletResponse response){
        ResultDTO<PageResult<List<PaperByUserEndVO>>> resultDTO = new ResultDTO<>();
        PageResult<List<PaperByUserEndVO>> listPageResult = examinationService.queryPaperByUserEnd(query);
        resultDTO.setResult(listPageResult);
        resultDTO.buildReturnCode(SystemCode.RET_CODE_SUCC, SystemCode.RET_MSG_SUCC);
        sendJsonSuccessPage(resultDTO, response);

    }
    /**
     * 导入题目
     */
    @RequestMapping("importTitle.htm")
    public void importTitle(MultipartFile file, HttpServletResponse response) {
        UserVO user = userUtil.getUser();
        examinationService.importTitle(file, user);
        sendJsonSuccess(response);
    }





}
