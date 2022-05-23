/**
 * 	Create on 2022 pekosmile
 *
 * 	All right reserved
 *
 * 	Create on 2022/5/20 05:20
 */
package cn.exam.dao.mapper.qte;

import cn.exam.dao.mapper.base.CommonBaseMapper;
import cn.exam.domain.qte.QteSubjectUserLink;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface QteSubjectUserLinkMapper
        extends CommonBaseMapper<QteSubjectUserLink> {


    List<QteSubjectUserLink> queryByList(Integer paperId);


}
