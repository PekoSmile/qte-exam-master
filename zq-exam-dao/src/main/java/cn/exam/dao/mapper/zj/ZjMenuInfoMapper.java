/**
 * 	Copyright 2020 www.kousi.cn
 *
 * 	All right reserved
 *
 * 	Create on 2020/5/20 05:20
 */
package cn.exam.dao.mapper.zj;


import cn.exam.dao.mapper.base.CommonBaseMapper;
import cn.exam.query.ZjMenuQuery;
import cn.exam.domain.zj.ZjMenuInfo;

import java.util.List;



public interface ZjMenuInfoMapper
        extends CommonBaseMapper<ZjMenuInfo> {
    List<ZjMenuInfo> queryPage(ZjMenuQuery query);

    List<Integer> queryMenuIdListByRoleId(String roleId);
}
