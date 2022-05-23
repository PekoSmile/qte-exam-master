/**
 * 	Create on 2022 pekosmile
 *
 * 	All right reserved
 *
 * 	Create on 2022/5/20 05:20
 */
package cn.exam.dao.mapper.qte;

import cn.exam.dao.mapper.base.CommonBaseMapper;
import cn.exam.vo.MenuInfoVO;
import cn.exam.domain.qte.QteRoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface QteRoleMenuMapper
        extends CommonBaseMapper<QteRoleMenu> {

    List<MenuInfoVO> queryMenuList(@Param("roleIdList") List<String> roleIdList);



    List<QteRoleMenu> queryRoleMenuInfoByRoleId(String roleId);

}
