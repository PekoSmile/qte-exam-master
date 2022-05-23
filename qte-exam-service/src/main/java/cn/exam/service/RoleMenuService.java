package cn.exam.service;

import cn.exam.vo.MenuInfoVO;

import java.util.List;

/**
 * @author pekosmile
 * @version 1.0
 * @date ##
 */
public interface RoleMenuService {


    /**
     * 根据角色id
     * @param roleIdList 角色列表
     */
    List<MenuInfoVO> queryMenuList(List<String> roleIdList);





}
