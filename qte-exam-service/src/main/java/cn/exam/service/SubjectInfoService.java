package cn.exam.service;

import cn.exam.domain.qte.QteSubjectInfo;
import cn.exam.query.SubjectQuery;
import cn.exam.util.PageResult;

import java.util.List;


public interface SubjectInfoService {

    void  insertSubject(QteSubjectInfo info);

    void  updateSubject(QteSubjectInfo info);

    void delete(QteSubjectInfo info);

    PageResult<List<QteSubjectInfo>> queryPageBySubject(SubjectQuery query);

    List<QteSubjectInfo> queryList();

}
