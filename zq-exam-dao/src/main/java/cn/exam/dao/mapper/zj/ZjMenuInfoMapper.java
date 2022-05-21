/**
 * 	Create on 2022 pekosmile
 *
 * 	All right reserved
 *
 * 	Create on 2022/5/20 05:20
 */
package cn.exam.dao.mapper.zj;


import cn.exam.dao.mapper.base.CommonBaseMapper;
import cn.exam.query.ZjMenuQuery;
import cn.exam.domain.zj.ZjMenuInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface ZjMenuInfoMapper
        extends CommonBaseMapper<ZjMenuInfo> {
    List<ZjMenuInfo> queryPage(ZjMenuQuery query);

    List<Integer> queryMenuIdListByRoleId(String roleId);
}
