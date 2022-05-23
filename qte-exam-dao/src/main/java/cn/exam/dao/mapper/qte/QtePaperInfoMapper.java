/**
 * 	Create on 2022 pekosmile
 *
 * 	All right reserved
 *
 * 	Create on 2022/5/20 05:20
 */
package cn.exam.dao.mapper.qte;

import cn.exam.dao.mapper.base.CommonBaseMapper;
import cn.exam.domain.qte.QtePaperInfo;
import cn.exam.query.PaperQuery;
import cn.exam.vo.PaperPageVO;
import cn.exam.vo.PaperTitleVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface QtePaperInfoMapper
        extends CommonBaseMapper<QtePaperInfo> {

    /**
     * 式卷页面
     * @return
     */
    List<PaperPageVO> queryPage(PaperQuery query);

    /**
     * 管理员  试卷页面
     * @param query
     * @return
     */
    List<PaperPageVO> queryManagerPage(PaperQuery query);

    List<PaperTitleVO> queryTitlePaper(Integer paperId);
}
