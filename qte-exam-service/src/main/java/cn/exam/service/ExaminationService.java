package cn.exam.service;

import cn.exam.domain.qte.QtePaperInfo;
import cn.exam.domain.qte.QteTitleInfo;
import cn.exam.query.PaperByUserEndQuery;
import cn.exam.query.PaperQuery;
import cn.exam.query.TitlePageQuery;
import cn.exam.util.PageResult;
import cn.exam.vo.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface ExaminationService {

    //试卷管理分页
    PageResult<List<PaperPageVO>> queryPage (PaperQuery query);

    //试卷管理分页
    PageResult<List<PaperPageVO>> queryManagerPage (PaperQuery query);


    PageResult<List<TitleVO>> queryPage(TitlePageQuery query);

    void insertTitle(QteTitleInfo info, UserVO user);

    TitleVO queryTitleInfo(Integer titleId);

    void deleteTitle(Integer titleId);

    void updateTitle(QteTitleInfo info);

    //试卷页面
    PaperTestLevel queryPaper(Integer paperId);

    //查看已考试卷
    PaperTestLevel queryPaperCompleted(Integer paperId,String userId);
    //自动组卷
    void autoPaper(QtePaperInfo paperInfo);

    //修改试题
    void updateTitle(String titleString);

    PageResult<List<PaperByUserEndVO>> queryPaperByUserEnd(PaperByUserEndQuery query);
    //导入试题
    void importTitle(MultipartFile file, UserVO userVO);



}
