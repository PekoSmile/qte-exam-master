/**
 * 	Create on 2022 pekosmile
 *
 * 	All right reserved
 *
 * 	Create on 2022/5/20 05:20
 */
package cn.exam.dao.mapper.qte;

import cn.exam.dao.mapper.base.CommonBaseMapper;
import cn.exam.domain.qte.QteUserInfo;
import cn.exam.query.UserQuery;
import cn.exam.vo.UserPageVO;
import cn.exam.vo.UserRoleVO;
import cn.exam.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface QteUserInfoMapper extends CommonBaseMapper<QteUserInfo> {
//    @Select("select  user_id userId,password,user_name userName from qte_user_info where user_id =#{userId} and is_delete=0")
    UserVO queryShiroUserInfoByUserName(@Param("userId") String userId);

    /**
     * userId查询权限
     * @param userId 用户id
     *
     */
    @Select("SELECT   a.user_id userId,a.role_id roleId,b.role_name  roleName FROM qte_user_role a LEFT JOIN qte_role b ON a.role_id = b.role_id WHERE a.user_id=#{userId}")
    List<UserRoleVO> queryUserRoleByUserId(@Param("userId")String userId);


    List<UserPageVO>queryPage(UserQuery query);

    List<QteUserInfo> queryListByClassId(Integer classId);

    Integer importUser(@Param(value = "userInfoList") List<QteUserInfo> userInfoList);






}
