package cn.exam.service.impl;

import cn.exam.dao.mapper.qte.*;
import cn.exam.domain.qte.*;
import cn.exam.query.PaperByUserEndQuery;
import cn.exam.query.PaperQuery;
import cn.exam.query.TitlePageQuery;
import cn.exam.service.ExaminationService;
import cn.exam.util.*;
import cn.exam.vo.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.registry.infomodel.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @version 1.0
 * @date ##
 */
@Service
@Transactional
public class ExaminationServiceImpl implements ExaminationService {

    @Autowired
    private QteTitleInfoMapper titleInfoMapper;
    @Autowired
    private QtePaperInfoMapper paperInfoMapper;
    @Autowired
    private QteSubjectUserLinkMapper userLinkMapper;
    @Autowired
    private QtePaperTestMapper paperTestMapper;
    @Autowired
    private QteUserInfoMapper userInfoMapper;
    @Autowired
    private QtePaperUserMapper paperUserMapper;


    @Override
    public PageResult<List<PaperByUserEndVO>> queryPaperByUserEnd(PaperByUserEndQuery query) {
        return PageUtil.execute(() -> paperUserMapper.queryPaperByUserEnd(query), query);
    }

    @Override
    public void importTitle(MultipartFile file, UserVO userVO) {
        String currentTime = DateUtils.getCurrentTime();
        try {
            List<QteTitleInfo> importTitleInfo = EasyExcelUtil.readExcelOneSheet(file.getInputStream(), QteTitleInfo.class);
            for (QteTitleInfo titleInfo : importTitleInfo) {
                titleInfo.setUserId(userVO.getUserId());
                titleInfo.setUserName(userVO.getUserName());
                titleInfo.setCreateTime(currentTime);
                titleInfo.setUpdateTime(currentTime);
            }
            titleInfoMapper.importTitleInfo(importTitleInfo);
        }catch(IOException e){
            throw new ExpressException(SystemCode.SERVICE_FAILD_CODE, "系统异常，导入失败");
        }
    }

    @Override
    public PaperTestLevel queryPaperCompleted(Integer paperId, String userId) {
        PaperTestLevel testLevel = new PaperTestLevel();
        List<TestLevelOne> oneList2 = new ArrayList<>();
        List<TestLevelOne> oneList3 = new ArrayList<>();
        List<PaperTitleVO> paperTitleVOS = paperInfoMapper.queryTitlePaper(paperId);
        List<QtePaperTest> qtePaperTests = paperTestMapper.queryPaperTestByUserIdAndPaperId(paperId, userId);
        HashMap<Integer,String> map = new HashMap<>();
        qtePaperTests.forEach(f->{
            map.put(f.getTitleId(),f.getAnswer());
        });

        //分析试卷
        List<PaperTitleVO> collect = paperTitleVOS.stream().filter(f -> f.getTitleStatus() == 0).collect(Collectors.toList());
        List<PaperTitleVO> collect1 = paperTitleVOS.stream().filter(f -> f.getTitleStatus() == 1).collect(Collectors.toList());
        List<PaperTitleVO> collect2 = paperTitleVOS.stream().filter(f -> f.getTitleStatus() == 2).collect(Collectors.toList());
        //单选题总分数
        if (!ObjectUtils.isEmpty(collect)) {
            List<TestLevelOne> oneList1 = new ArrayList<>();
            for (PaperTitleVO titleVO : collect) {
                TestLevelOne levelOne = new TestLevelOne();
                levelOne.setTitleName(titleVO.getTitleName());
                levelOne.setId(titleVO.getTitleId());
                levelOne.setTitleFraction(titleVO.getFraction());
                levelOne.setChoice1(titleVO.getChoice1());
                levelOne.setChoice2(titleVO.getChoice2());
                levelOne.setChoice3(titleVO.getChoice3());
                levelOne.setChoice4(titleVO.getChoice4());
                levelOne.setAnswer(titleVO.getTitleAnswer());
                map.forEach((k,v)->{
                    if (k.equals(titleVO.getTitleId())){
                        levelOne.setStudentAnswers(v);
                    }
                });
                oneList1.add(levelOne);
            }
            testLevel.setOneList1(oneList1);
        }
        //填空
        if (!ObjectUtils.isEmpty(collect1)) {
            for (PaperTitleVO titleVO : collect1) {
                TestLevelOne levelOne = new TestLevelOne();
                levelOne.setTitleName(titleVO.getTitleName());
                levelOne.setId(titleVO.getTitleId());
                levelOne.setTitleFraction(titleVO.getFraction());
                levelOne.setAnswer(titleVO.getTitleAnswer());
                map.forEach((k,v)->{
                    if (k.equals(titleVO.getTitleId())){
                        levelOne.setStudentAnswers(v);
                    }
                });
                oneList2.add(levelOne);
            }
            testLevel.setOneList2(oneList2);
        }
        //判断
        if (!ObjectUtils.isEmpty(collect2)) {
            for (PaperTitleVO titleVO : collect2) {
                TestLevelOne levelOne = new TestLevelOne();
                levelOne.setTitleName(titleVO.getTitleName());
                levelOne.setId(titleVO.getTitleId());
                levelOne.setTitleFraction(titleVO.getFraction());
                levelOne.setAnswer(titleVO.getTitleAnswer());
                map.forEach((k,v)->{
                    if (k.equals(titleVO.getTitleId())){
                        levelOne.setStudentAnswers(v);
                    }
                });
                oneList3.add(levelOne);
            }
            testLevel.setOneList3(oneList3);
        }
        return testLevel;
    }
    @Override
    public PageResult<List<PaperPageVO>> queryPage(PaperQuery query) {
        return PageUtil.execute(() -> paperInfoMapper.queryPage(query), query);
    }

    @Override
    public PageResult<List<PaperPageVO>> queryManagerPage(PaperQuery query) {
        return PageUtil.execute(() -> paperInfoMapper.queryManagerPage(query), query);
    }

    @Override
    public PageResult<List<TitleVO>> queryPage(TitlePageQuery query) {
        return PageUtil.execute(() -> titleInfoMapper.queryPage(query), query);
    }

    @Override
    public void insertTitle(QteTitleInfo info, UserVO user) {
        String currentDateTime = DateUtil.getCurrentDateTime();
        info.setUserId(user.getUserId());
        info.setUserName(user.getUserName());
        info.setCreateTime(currentDateTime);
        info.setUpdateTime(currentDateTime);
        titleInfoMapper.insertSelective(info);
    }

    @Override
    public TitleVO queryTitleInfo(Integer titleId) {
        if (titleId == null) {
            throw new ExpressException(SystemCode.SERVICE_FAILD_CODE, "题目id为空");
        }
        return titleInfoMapper.queryTitleInfo(titleId);
    }

    @Override
    public void deleteTitle(Integer titleId) {
        if (titleId == null) {
            throw new ExpressException(SystemCode.SERVICE_FAILD_CODE, "题目id为空");
        }
        titleInfoMapper.deleteByPrimaryKey(titleId);
    }

    @Override
    public void updateTitle(QteTitleInfo info) {
        String currentDateTime = DateUtil.getCurrentDateTime();
        info.setUpdateTime(currentDateTime);
        titleInfoMapper.updateByPrimaryKeySelective(info);
    }

    @Override
    public PaperTestLevel queryPaper(Integer paperId) {
        PaperTestLevel testLevel = new PaperTestLevel();
        List<TestLevelOne> oneList2 = new ArrayList<>();
        List<TestLevelOne> oneList3 = new ArrayList<>();
        List<PaperTitleVO> paperTitleVOS = paperInfoMapper.queryTitlePaper(paperId);
        //分析试卷
        List<PaperTitleVO> collect = paperTitleVOS.stream().filter(f -> f.getTitleStatus() == 0).collect(Collectors.toList());
        List<PaperTitleVO> collect1 = paperTitleVOS.stream().filter(f -> f.getTitleStatus() == 1).collect(Collectors.toList());
        List<PaperTitleVO> collect2 = paperTitleVOS.stream().filter(f -> f.getTitleStatus() == 2).collect(Collectors.toList());
        //单选题总分数
        if (!ObjectUtils.isEmpty(collect)) {
            List<TestLevelOne> oneList1 = new ArrayList<>();
            for (PaperTitleVO titleVO : collect) {
                TestLevelOne levelOne = new TestLevelOne();
                levelOne.setTitleName(titleVO.getTitleName());
                levelOne.setId(titleVO.getTitleId());
                levelOne.setTitleFraction(titleVO.getFraction());
                levelOne.setChoice1(titleVO.getChoice1());
                levelOne.setChoice2(titleVO.getChoice2());
                levelOne.setChoice3(titleVO.getChoice3());
                levelOne.setChoice4(titleVO.getChoice4());
                levelOne.setAnswer(titleVO.getTitleAnswer());
                oneList1.add(levelOne);
            }
            testLevel.setOneList1(oneList1);
        }
        //填空
        if (!ObjectUtils.isEmpty(collect1)) {
            for (PaperTitleVO titleVO : collect1) {
                TestLevelOne levelOne = new TestLevelOne();
                levelOne.setTitleName(titleVO.getTitleName());
                levelOne.setId(titleVO.getTitleId());
                levelOne.setTitleFraction(titleVO.getFraction());
                levelOne.setAnswer(titleVO.getTitleAnswer());
                oneList2.add(levelOne);
            }
            testLevel.setOneList2(oneList2);
        }
        //判断
        if (!ObjectUtils.isEmpty(collect2)) {


            for (PaperTitleVO titleVO : collect2) {
                TestLevelOne levelOne = new TestLevelOne();
                levelOne.setTitleName(titleVO.getTitleName());
                levelOne.setId(titleVO.getTitleId());
                levelOne.setTitleFraction(titleVO.getFraction());
                levelOne.setAnswer(titleVO.getTitleAnswer());
                oneList3.add(levelOne);
            }
            testLevel.setOneList3(oneList3);
        }
        return testLevel;
    }

    /**
     * 组卷
     **/

    @Override
    public void autoPaper(QtePaperInfo paperInfo) {
        String currentDateTime = DateUtil.getCurrentDateTime();
/**
 * 通过科目ID查询科目多少试题
 **/
        List<QteTitleInfo> qteTitleInfos = titleInfoMapper.queryTitleBySubjectId(paperInfo.getSubjectId());
        if (qteTitleInfos.size() == 0) {
            throw new ExpressException(SystemCode.SERVICE_FAILD_CODE, "该科目试题不够");
        }
        int sum = qteTitleInfos.stream().mapToInt(QteTitleInfo::getTitleFraction).sum();
        /**jdk8 Stream流处理计算出这个集合里所有的分数**/
        if (sum < 100) {
            throw new ExpressException(SystemCode.SERVICE_FAILD_CODE, "该科目试题分数不够100分");
        }
        List<QteTitleInfo> qteTitleInfos1 = titleInfoMapper.queryTitleByDifficulty(paperInfo.getDifficulty() - 2, paperInfo.getDifficulty() + 2, paperInfo.getSubjectId());
        int result1 = qteTitleInfos1.stream().mapToInt(QteTitleInfo::getTitleFraction).sum();
        if (result1 < 100) {
            throw new ExpressException(SystemCode.SERVICE_FAILD_CODE, "该难度的科目试题不够(分数不够组卷)");
        }
        List<QteTitleInfo> qteTitleInfoList = new ArrayList<>();
        //过滤所有单选
        List<QteTitleInfo> collect = qteTitleInfos1.stream().filter(f -> f.getTitleStatus() == 0).collect(Collectors.toList());
        /**
         * 初始题库每次查出来的题目乱序，如果选择题只有10个那么  10道题乱序。如果20道  20题乱序然后抽取
         */
        //System.out.println(JSON.toJSONString(collect));
        //乱序
        Collections.shuffle(collect);
        //System.out.println("----------------------------------------");
        //System.out.println(JSON.toJSONString(collect));
        if(collect.size() >=20) {
            List<QteTitleInfo> qteTitleInfoList1 = collect.subList(0, 20);
            //截取二十个单选题
            qteTitleInfoList1.forEach(f -> {
                QteTitleInfo titleInfo = new QteTitleInfo();
                BeanUtils.copyProperties(f, titleInfo);
                qteTitleInfoList.add(titleInfo);
            });
        }else{
            throw new ExpressException(SystemCode.SERVICE_FAILD_CODE, "选择题组卷题量分数不足");
        }

        //填空
        List<QteTitleInfo> collect1 = qteTitleInfos1.stream().filter(f -> f.getTitleStatus() == 1 ).collect(Collectors.toList());
        Collections.shuffle(collect1);
        if(collect1.size() >=5) {
            List<QteTitleInfo> qteTitleInfoList2 = collect1.subList(0, 5);
            //截取五个
            qteTitleInfoList2.forEach(f -> {
                QteTitleInfo titleInfo = new QteTitleInfo();
                BeanUtils.copyProperties(f, titleInfo);
                qteTitleInfoList.add(titleInfo);
            });
        }else{
            throw new ExpressException(SystemCode.SERVICE_FAILD_CODE, "填空题组卷题量分数不足");
        }
        //判断
        List<QteTitleInfo> collect2 = qteTitleInfos1.stream().filter(f -> f.getTitleStatus() == 2 ).collect(Collectors.toList());
        Collections.shuffle(collect2);
        if(collect1.size() >=5) {
            List<QteTitleInfo> qteTitleInfoList3 = collect2.subList(0, 5);
            //截取五个
            qteTitleInfoList3.forEach(f -> {
                QteTitleInfo titleInfo = new QteTitleInfo();
                BeanUtils.copyProperties(f, titleInfo);
                qteTitleInfoList.add(titleInfo);
            });
        }else{
            throw new ExpressException(SystemCode.SERVICE_FAILD_CODE, "判断题组卷题量分数不足");
        }
        int total= qteTitleInfoList.stream().mapToInt(QteTitleInfo::getTitleFraction).sum();
        if (total != 100) {
            throw new ExpressException(SystemCode.SERVICE_FAILD_CODE, "组卷失败！请检查题库");

        }

//        List<QteTitleInfo> collect1 = qteTitleInfos1.stream().filter(f -> f.getTitleStatus() == 1 ).collect(Collectors.toList());
//        for (QteTitleInfo titleInfo : collect1) {
//            int sum1 = qteTitleInfoList.stream().mapToInt(QteTitleInfo::getTitleFraction).sum();
//            if (sum1 + titleInfo.getTitleFraction() <= 100) {
//                qteTitleInfoList.add(titleInfo);
//            } else if (sum1 + titleInfo.getTitleFraction() > 100) {
//                break;
//            }
//        }
//        int sum1 = qteTitleInfoList.stream().mapToInt(QteTitleInfo::getTitleFraction).sum();
////        if (sum1 < 100) {
////            throw new ExpressException(SystemCode.SERVICE_FAILD_CODE, "分数不足不能组卷");
////
////        }
        paperInfo.setPaperScore(100);
        paperInfo.setCreateTime(currentDateTime);
        paperInfo.setUpdateTime(currentDateTime);
        paperInfoMapper.insertSelective(paperInfo);
        qteTitleInfoList.forEach(f -> {
            QteSubjectUserLink userLink = new QteSubjectUserLink();
            userLink.setClassId(paperInfo.getClassId());
            userLink.setPaperId(paperInfo.getPaperId());
            userLink.setTitleId(f.getTitleId());
            userLink.setCreateTime(currentDateTime);
            userLink.setUpdateTime(currentDateTime);
            userLinkMapper.insertSelective(userLink);
        });
        //组卷完成给这个班级里面的所有学生 生成试卷
        List<QteUserInfo> qteUserInfos = userInfoMapper.queryListByClassId(paperInfo.getClassId());
        List<QteSubjectUserLink> qteSubjectUserLinks = userLinkMapper.queryByList(paperInfo.getPaperId());
        List<Integer> titleIdList = new ArrayList<>();
        if (!ObjectUtils.isEmpty(qteSubjectUserLinks)) {
            qteSubjectUserLinks.forEach(f -> {
                titleIdList.add(f.getTitleId());
            });
        }

        QteSubjectUserLink userLink = qteSubjectUserLinks.get(0);
        List<QteTitleInfo> qteTitleInfos2 = titleInfoMapper.queryListByTitleId(titleIdList);
        List<QtePaperTest> paperTests = new ArrayList<>();
        qteUserInfos.forEach(y -> {
            qteTitleInfos2.forEach(f -> {
                QtePaperTest paperTest = new QtePaperTest();
                paperTest.setTitleAnswer(f.getTitleAnswer());
                paperTest.setClassId(userLink.getClassId());
                paperTest.setPaperId(paperInfo.getPaperId());
                paperTest.setTitleFraction(f.getTitleFraction());
                paperTest.setTitleId(f.getTitleId());
                paperTest.setUserId(y.getUserId());
                paperTest.setUserName(y.getUserName());
                paperTest.setCreateTime(DateUtil.getCurrentDateTime());
                paperTests.add(paperTest);
            });
            QtePaperUser paperUser = new QtePaperUser();
            paperUser.setPaperId(paperInfo.getPaperId());
            paperUser.setUserId(y.getUserId());
            paperUserMapper.insertSelective(paperUser);
        });
        paperTestMapper.insertList(paperTests);
    }

    @Override
    public void updateTitle(String titleString) {
        String str = "[" + titleString + "]";
        JSONArray objects = JSON.parseArray(str);
        List<Integer> ids = new ArrayList<>();
        for (Object obj : objects) {
            QteTitleInfo paperTest = new QteTitleInfo();
            JSONObject object = JSON.parseObject(obj.toString());
            Object id = object.get("id");
            Object answer = object.get("answer");
            if (!ObjectUtils.isEmpty(id)) {
                paperTest.setTitleId(Integer.valueOf(id.toString()));
                ids.add(Integer.valueOf(id.toString()));
            }
            if (!ObjectUtils.isEmpty(answer)) {
                paperTest.setTitleAnswer(answer.toString());
                titleInfoMapper.updateByPrimaryKeySelective(paperTest);
            }
        }
    }
}