/**
 * 	Create on 2022 pekosmile
 *
 * 	All right reserved
 *
 * 	Create on 2022/5/20 05:20
 */
package cn.exam.dao.mapper.zj;

import cn.exam.dao.mapper.base.CommonBaseMapper;
import cn.exam.domain.zj.ZjPaperUser;
import cn.exam.query.PaperByUserEndQuery;
import cn.exam.query.PaperUserQuery;
import cn.exam.vo.AchievementExportVO;
import cn.exam.vo.PaperByUserEndVO;
import cn.exam.vo.PaperExportVO;
import cn.exam.vo.PaperUserPapage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface ZjPaperUserMapper
        extends CommonBaseMapper<ZjPaperUser> {


    ZjPaperUser queryPaper(ZjPaperUser paperUser);

    List<PaperUserPapage> queryPage(PaperUserQuery query);

    /**
     * 成绩导出
     */
    List<AchievementExportVO> queryExport();


    /**
     * 导出
     */
    List<PaperExportVO> queryPaperExport(Integer paperId);

    /**
     * 查询考完试卷
     *
     //* @param userId
     */
    List<PaperByUserEndVO> queryPaperByUserEnd(PaperByUserEndQuery query);

}
