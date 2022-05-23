package cn.exam.service;

import cn.exam.domain.qte.QteClassInfo;
import cn.exam.query.ClassQuery;
import cn.exam.util.PageResult;

import java.util.List;

/**
 * @version 1.0
 * @date ##
 */
public interface ClassInfoService {

    PageResult<List<QteClassInfo>> queryPage(ClassQuery query);


    void insert(QteClassInfo classInfo);

    void delete(Integer id);
}
