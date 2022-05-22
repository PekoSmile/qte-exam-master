/**
 * 	Create on 2022 pekosmile
 *
 * 	All right reserved
 *
 * 	Create on 2022/5/20 05:20
 */
package cn.exam.dao.mapper.zj;

import cn.exam.dao.mapper.base.CommonBaseMapper;
import cn.exam.domain.zj.ZjUserInfo;
import cn.exam.domain.zj.ZjUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface ZjUserRoleMapper
        extends CommonBaseMapper<ZjUserRole> {
    Integer importRole(@Param(value = "roleInfoList") List<ZjUserRole> roleInfoList);
}
