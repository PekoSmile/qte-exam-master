package cn.exam.service;

import cn.exam.query.PaperUserQuery;
import cn.exam.so.PaperSuccessSO;
import cn.exam.util.PageResult;
import cn.exam.vo.AchievementExportVO;
import cn.exam.vo.PaperExportVO;
import cn.exam.vo.PaperTestLevel;
import cn.exam.vo.PaperUserPapage;

import java.util.List;

/**
 * @author pekosmile
 * @version 1.0
 * @date ##
 */
public interface PaperTestService {

    PaperTestLevel paperTest(Integer paperId,String userId);

    List<Integer> queryListIdByPaperId(Integer paperId);

    void paperEnd(PaperSuccessSO successSO);

    PageResult<List<PaperUserPapage>> queryPaperUser(PaperUserQuery query);
    List<String> queryAchievement(PaperUserQuery query);

    List<PaperUserQuery> queryClassList();

    /**
     * 试卷导出
     */
    List<PaperExportVO> queryPaperExport(Integer paperId);

    /**
     * 成绩导出
     * @return
     */
    List<AchievementExportVO> queryExport();



}
