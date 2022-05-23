/**
 * 	Create on 2022 pekosmile
 *
 * 	All right reserved
 *
 * 	Create on 2022/5/20 05:20
 */
package cn.exam.dao.mapper.qte;

import cn.exam.dao.mapper.base.CommonBaseMapper;
import cn.exam.domain.qte.QteTitleInfo;
import cn.exam.query.TitlePageQuery;
import cn.exam.vo.TitleVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface QteTitleInfoMapper
        extends CommonBaseMapper<QteTitleInfo> {

    List<TitleVO> queryPage(TitlePageQuery query);


    TitleVO queryTitleInfo(Integer titleId);
    //classId 查询试题
    List<QteTitleInfo> queryTitleBySubjectId(@Param("subjectId")Integer subjectId);
    //在一个难度区间
    List<QteTitleInfo> queryTitleByDifficulty(@Param("difficulty1") Double difficulty1, @Param("difficulty2") Double difficulty2, @Param("subjectId") Integer subjectId);

    List<QteTitleInfo> queryListByTitleId(@Param("titleIdList") List<Integer> titleIdList);

    List<QteTitleInfo> queryListByTitleIdE(@Param("titleIdList") List<Integer> titleIdList);
    Integer importTitleInfo(@Param("importTitleList")List<QteTitleInfo> importTitleList);
}
