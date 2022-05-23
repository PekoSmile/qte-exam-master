package cn.exam.service;

import cn.exam.domain.qte.QteRole;
import cn.exam.query.RoleQuery;
import cn.exam.util.PageResult;

import java.util.List;

/**
 * @author pekosmile
 * @version 1.0
 * @date ##
 */
public interface RoleService {
    /**
     * 分页
     */
    PageResult<List<QteRole>> queryPage(RoleQuery query);


    void insertRoleInfo(QteRole role);

    void deleteRole(Integer id);
}
