/**
 * 	Copyright 2020 www.zj.cn
 *
 * 	All right reserved
 *
 * 	Create on 2020/5/20 05:20
 */
package cn.exam.dao.mapper.zj;

import cn.exam.dao.mapper.base.CommonBaseMapper;
import cn.exam.vo.MenuInfoVO;
import cn.exam.domain.zj.ZjRoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;



public interface ZjRoleMenuMapper
        extends CommonBaseMapper<ZjRoleMenu> {

    List<MenuInfoVO> queryMenuList(@Param("roleIdList") List<String> roleIdList);



    List<ZjRoleMenu> queryRoleMenuInfoByRoleId(String roleId);

}
