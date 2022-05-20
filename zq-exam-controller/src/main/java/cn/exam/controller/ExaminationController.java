package cn.exam.controller;

import cn.exam.config.BaseController;
import cn.exam.config.UserUtil;
import cn.exam.dao.mapper.zj.ZjPaperTestMapper;
import cn.exam.dao.mapper.zj.ZjSubjectUserLinkMapper;
import cn.exam.dao.mapper.zj.ZjTitleInfoMapper;
import cn.exam.domain.zj.*;
import cn.exam.query.PaperByUserIdQuery;
import cn.exam.query.PaperQuery;
import cn.exam.query.TitlePageQuery;
import cn.exam.service.ExaminationService;
import cn.exam.util.DateUtil;
import cn.exam.util.PageResult;
import cn.exam.util.ResultDTO;
import cn.exam.util.SystemCode;
import cn.exam.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
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
    public void insertByTitle(ZjTitleInfo info, HttpServletResponse response) {
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
    public void updateTitle(HttpServletResponse response,ZjTitleInfo info){
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
    public void   queryPaperCompleted(Integer paperId,HttpServletResponse response) {
        ResultDTO<PaperTestLevel> resultDTO = new ResultDTO<>();
        UserVO user = userUtil.getUser();
        resultDTO.setResult(examinationService.queryPaperCompleted(paperId,user.getUserId()));
        resultDTO.buildReturnCode(SystemCode.RET_CODE_SUCC, SystemCode.RET_MSG_SUCC);
        sendJsonSuccess(resultDTO, response);
    }

    //组卷功能
    @RequestMapping("autoExam.htm")
    public void autoExam(ZjPaperInfo paperInfo,HttpServletResponse response){
        UserVO user = userUtil.getUser();
        paperInfo.setTeachId(user.getUserId());
        paperInfo.setTeachName(user.getUserName());
        examinationService.autoPaper(paperInfo);
        sendJsonSuccess( response);
    }

    @RequestMapping("updateTitleByList.htm")
    public void updateTitleByList(String titleString,HttpServletResponse response){
        examinationService.updateTitle(titleString);
        sendJsonSuccess(response);
    }

    /**
     * 学生已考试卷查询分页
     */
    @RequestMapping("queryPaperByUserId.htm")
    public void queryPaperByUserId(PaperByUserIdQuery query, HttpServletResponse response){
        ResultDTO<PageResult<List<PaperByUserIdVO>>> resultDTO = new ResultDTO<>();
        UserVO user = userUtil.getUser();
        query.setUserId(user.getUserId());
        PageResult<List<PaperByUserIdVO>> listPageResult = examinationService.queryPaperByUserId(query);
        resultDTO.setResult(listPageResult);
        resultDTO.buildReturnCode(SystemCode.RET_CODE_SUCC, SystemCode.RET_MSG_SUCC);
        sendJsonSuccessPage(resultDTO, response);

    }



}
