/**
 * 	Create on 2022 pekosmile
 *
 * 	All right reserved
 *
 * 	Create on 2022/5/20 05:20
 */
package cn.exam.dao.mapper.qte;

import cn.exam.dao.mapper.base.CommonBaseMapper;
import cn.exam.domain.qte.QtePaperTest;
import cn.exam.vo.PaperTestVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface QtePaperTestMapper
        extends CommonBaseMapper<QtePaperTest> {

    List<PaperTestVO> queryPaperTest(@Param("userId") String userId,@Param("paperId") Integer paperId);

    Integer ResetAnswer(Integer id);

    List<Integer> queryIdByPaperId(Integer paperId);

    List<QtePaperTest> queryListById(@Param("ids")List<Integer> ids);

    List<QtePaperTest> queryPaperTestByUserIdAndPaperId(@Param("paperId") Integer paperId, @Param("userId") String userId);
}
