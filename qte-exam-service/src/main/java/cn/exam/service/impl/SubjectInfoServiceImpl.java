package cn.exam.service.impl;

import cn.exam.dao.mapper.qte.QteSubjectInfoMapper;
import cn.exam.domain.qte.QteSubjectInfo;
import cn.exam.query.SubjectQuery;
import cn.exam.service.SubjectInfoService;
import cn.exam.util.DateUtil;
import cn.exam.util.PageResult;
import cn.exam.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectInfoServiceImpl implements SubjectInfoService {
    @Autowired
    private QteSubjectInfoMapper subjectInfoMapper;


    @Override
    public void insertSubject(QteSubjectInfo info) {
        String currentDateTime = DateUtil.getCurrentDateTime();
        info.setCreateTime(currentDateTime);
        info.setUpdateTime(currentDateTime);
        subjectInfoMapper.insertSelective(info);

    }

    @Override
    public void updateSubject(QteSubjectInfo info) {
        String currentDateTime = DateUtil.getCurrentDateTime();
        info.setCreateTime(currentDateTime);
        info.setUpdateTime(currentDateTime);
        subjectInfoMapper.updateByPrimaryKeySelective(info);

    }

    @Override
    public void delete(QteSubjectInfo info) {
        subjectInfoMapper.delete(info);
    }

    @Override
    public PageResult<List<QteSubjectInfo>> queryPageBySubject(SubjectQuery query) {
        return PageUtil.execute(()->subjectInfoMapper.queryPageBySubject(query),query);
    }

    @Override
    public List<QteSubjectInfo> queryList() {
        SubjectQuery query = new SubjectQuery();
        return subjectInfoMapper.queryPageBySubject(query);
    }
}
