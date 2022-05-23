package cn.exam.service.impl;

import cn.exam.dao.mapper.qte.QteClassInfoMapper;
import cn.exam.domain.qte.QteClassInfo;
import cn.exam.query.ClassQuery;
import cn.exam.service.ClassInfoService;
import cn.exam.util.PageResult;
import cn.exam.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pekosmile
 * @version 1.0
 * @date ##
 */
@Service
public class ClassInfoServiceImpl implements ClassInfoService {
    @Autowired
    private QteClassInfoMapper classInfoMapper;
    @Override
    public PageResult<List<QteClassInfo>> queryPage(ClassQuery query) {
        return PageUtil.execute(()->classInfoMapper.queryPage(query),query);
    }

    @Override
    public void insert(QteClassInfo classInfo) {
        classInfoMapper.insert(classInfo);

    }

    @Override
    public void delete(Integer id) {
        classInfoMapper.deleteByPrimaryKey(id);
    }
}
