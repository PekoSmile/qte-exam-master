/**
 * 	Create on 2022 pekosmile
 *
 * 	All right reserved
 *
 * 	Create on 2022/5/20 05:20
 */
package cn.exam.dao.mapper.zj;

import cn.exam.dao.mapper.base.CommonBaseMapper;
import cn.exam.domain.zj.ZjSubjectUserLink;

import java.util.List;



public interface ZjSubjectUserLinkMapper
        extends CommonBaseMapper<ZjSubjectUserLink> {


    List<ZjSubjectUserLink> queryByList(Integer paperId);


}
