/**
 * 	Copyright 2020 www.zj.cn
 *
 * 	All right reserved
 *
 * 	Create on 2020/5/20 05:20
 */
package cn.exam.dao.mapper.zj;

import cn.exam.dao.mapper.base.CommonBaseMapper;
import cn.exam.domain.zj.ZjTitleInfo;
import cn.exam.query.TitlePageQuery;
import cn.exam.vo.TitleVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;



public interface ZjTitleInfoMapper
        extends CommonBaseMapper<ZjTitleInfo> {

    List<TitleVO> queryPage(TitlePageQuery query);


    TitleVO queryTitleInfo(Integer titleId);
    //classId 查询试题
    List<ZjTitleInfo> queryTitleByClassId(@Param("classId") Integer classId,@Param("subjectId")Integer subjectId);
    //在一个难度区间
    List<ZjTitleInfo> queryTitleByDifficulty(@Param("difficulty1") Double difficulty1,@Param("difficulty2") Double difficulty2,@Param("classId") Integer classId);

    List<ZjTitleInfo> queryListByTitleId(@Param("titleIdList") List<Integer> titleIdList);

    List<ZjTitleInfo> queryListByTitleIdE(@Param("titleIdList") List<Integer> titleIdList);
}
