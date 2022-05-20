/**
 * 	Create on 2022 pekosmile
 *
 * 	All right reserved
 *
 * 	Create on 2022/5/20 05:20
 */
package cn.exam.dao.mapper.zj;

import cn.exam.dao.mapper.base.CommonBaseMapper;
import cn.exam.domain.zj.ZjClassInfo;
import cn.exam.query.ClassQuery;

import java.util.List;


public interface ZjClassInfoMapper
        extends CommonBaseMapper<ZjClassInfo> {


    List<ZjClassInfo> queryPage(ClassQuery query);
}
