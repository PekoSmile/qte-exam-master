package cn.exam.service.impl;

import cn.exam.dao.mapper.qte.QtePaperTestMapper;
import cn.exam.dao.mapper.qte.QtePaperUserMapper;
import cn.exam.domain.qte.QtePaperTest;
import cn.exam.domain.qte.QtePaperUser;
import cn.exam.query.PaperUserQuery;
import cn.exam.service.PaperTestService;
import cn.exam.so.PaperSuccessSO;
import cn.exam.util.PageResult;
import cn.exam.util.PageUtil;
import cn.exam.vo.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @version 1.0
 * @date ##
 */
@Service
@Transactional
public class PaperTestServiceImpl implements PaperTestService {
    @Autowired
    private QtePaperTestMapper paperTestMapper;
    @Autowired
    private QtePaperUserMapper paperUserMapper;


    @Override
    public PaperTestLevel paperTest(Integer paperId, String userId) {
        PaperTestLevel testLevel = new PaperTestLevel();
        List<PaperTestVO> paperTestVOS = paperTestMapper.queryPaperTest(userId, paperId);
        PaperTestVO paperTestVO = paperTestVOS.get(0);
        //分析试卷
        List<PaperTestVO> collect = paperTestVOS.stream().filter(f -> f.getTitleStatus() == 0).collect(Collectors.toList());
        List<PaperTestVO> collect1 = paperTestVOS.stream().filter(f -> f.getTitleStatus() == 1).collect(Collectors.toList());
        List<PaperTestVO> collect2 = paperTestVOS.stream().filter(f -> f.getTitleStatus() == 2).collect(Collectors.toList());
        //所有题目总分
        int sum3 = paperTestVOS.stream().mapToInt(PaperTestVO::getTitleFraction).sum();
        //单选题总分
        int sum = collect.stream().mapToInt(PaperTestVO::getTitleFraction).sum();
        //填空题总分
        int sum1 = collect1.stream().mapToInt(PaperTestVO::getTitleFraction).sum();
        //判断题总分
        int sum2 = collect2.stream().mapToInt(PaperTestVO::getTitleFraction).sum();
        testLevel.setExamDate(paperTestVO.getExamDate());
        testLevel.setPaperName(paperTestVO.getPaperName());
        testLevel.setUserName(paperTestVO.getUserName());
        testLevel.setTotalScore(sum3);
        testLevel.setFractionSum(sum);
        testLevel.setFractionSum1(sum1);
        testLevel.setFractionSum2(sum2);
        testLevel.setTitleNum(collect.size());
        testLevel.setTitleNum1(collect1.size());
        testLevel.setTitleNum2(collect2.size());
        List<TestLevelOne> oneList1 = new ArrayList<>();
        List<TestLevelOne> oneList2 = new ArrayList<>();
        List<TestLevelOne> oneList3 = new ArrayList<>();
        paperTestVOS.forEach(f -> {
            //单选
            if (f.getTitleStatus() == 0) {
                TestLevelOne levelOne = new TestLevelOne();
                levelOne.setTitleName(f.getTitleName());
                levelOne.setTitleFraction(f.getTitleFraction());
                levelOne.setChoice1(f.getChoice1());
                levelOne.setChoice2(f.getChoice2());
                levelOne.setChoice3(f.getChoice3());
                levelOne.setChoice4(f.getChoice4());
                levelOne.setId(f.getId());
                oneList1.add(levelOne);
            } else if (f.getTitleStatus() == 1) {
                TestLevelOne levelOne = new TestLevelOne();
                levelOne.setTitleName(f.getTitleName());
                levelOne.setTitleFraction(f.getTitleFraction());
                levelOne.setId(f.getId());
                oneList2.add(levelOne);
            } else if (f.getTitleStatus() == 2) {
                TestLevelOne levelOne = new TestLevelOne();
                levelOne.setTitleName(f.getTitleName());
                levelOne.setId(f.getId());
                levelOne.setTitleFraction(f.getTitleFraction());
                oneList3.add(levelOne);
            }
        });
        testLevel.setOneList1(oneList1);
        testLevel.setOneList2(oneList2);
        testLevel.setOneList3(oneList3);
        return testLevel;
    }

    @Override
    public List<Integer> queryListIdByPaperId(Integer paperId) {
        return paperTestMapper.queryIdByPaperId(paperId);
    }

    @Override
    public void paperEnd(PaperSuccessSO successSO) {
        String paperList = successSO.getPaperList();
        String str = "[" + paperList + "]";
        JSONArray objects = JSON.parseArray(str);
        List<Integer> ids = new ArrayList<>();
        for (Object obj : objects) {
            QtePaperTest paperTest = new QtePaperTest();
            JSONObject object = JSON.parseObject(obj.toString());
            Object id = object.get("id");
            Object val = object.get("val");
            if (!ObjectUtils.isEmpty(id)) {
                paperTest.setId(Integer.valueOf(id.toString()));
                ids.add(Integer.valueOf(id.toString()));
            }
            if (!ObjectUtils.isEmpty(val)) {
                paperTest.setAnswer(val.toString());
                paperTestMapper.updateByPrimaryKeySelective(paperTest);
            }else {
                paperTestMapper.ResetAnswer((Integer) id);
            }
        }
        List<QtePaperTest> paperTests = paperTestMapper.queryListById(ids);
        Integer fraction = 0;
        for (QtePaperTest f : paperTests) {
            String all = f.getTitleAnswer().replaceAll(" ", "");
            if (!ObjectUtils.isEmpty(f.getAnswer())) {
                String all1 = f.getAnswer().replaceAll(" ", "");
                if (all.equals(all1)) {
                    fraction = fraction + f.getTitleFraction();
                }
            }
        }
        /**
         * 查询考试人员成绩表
         */
        QtePaperUser paperUser = new QtePaperUser();
        paperUser.setPaperId(successSO.getPaperId());
        paperUser.setUserId(successSO.getUserId());
        QtePaperUser paperUser1 = paperUserMapper.queryPaper(paperUser);
        //修改考试信息成绩
        paperUser1.setFraction(fraction);
        paperUser1.setStatus(1);
        paperUserMapper.updateByPrimaryKeySelective(paperUser1);
    }

    @Override
    public PageResult<List<PaperUserPapage>> queryPaperUser(PaperUserQuery query) {
        return PageUtil.execute(() -> paperUserMapper.queryPage(query), query);
    }

    @Override
    public List<String> queryAchievement(PaperUserQuery query) {
        List<String> strings = new ArrayList<>();
        List<PaperUserPapage> paperUserPapages = paperUserMapper.queryPage(query);
        List<PaperUserPapage> collect = paperUserPapages.stream().filter(f -> f.getFraction() != null).collect(Collectors.toList());
        List<PaperUserPapage> collect1 = collect.stream().filter(f -> f.getFraction() <= 30).collect(Collectors.toList());
        strings.add(String.valueOf(collect1.size()));
        //大于30 小于等于60
        List<PaperUserPapage> collect2 = collect.stream().filter(f -> f.getFraction() > 30).collect(Collectors.toList());
        List<PaperUserPapage> collect3 = collect2.stream().filter(f -> f.getFraction() <= 60).collect(Collectors.toList());
        strings.add(String.valueOf(collect3.size()));
        //大于60小于等于80
        List<PaperUserPapage> collect4 = collect.stream().filter(f -> f.getFraction() > 60).collect(Collectors.toList());
        List<PaperUserPapage> collect5 = collect4.stream().filter(f -> f.getFraction() <= 80).collect(Collectors.toList());
        strings.add(String.valueOf(collect5.size()));
        //100
        List<PaperUserPapage> collect6 = collect.stream().filter(f -> f.getFraction() > 80).collect(Collectors.toList());
        List<PaperUserPapage> collect7 = collect6.stream().filter(f -> f.getFraction() <= 100).collect(Collectors.toList());
        strings.add(String.valueOf(collect7.size()));
        return strings;
    }

    @Override
    public List<PaperUserQuery> queryClassList() {
        List<PaperUserPapage> paperUserPapages = paperUserMapper.queryPage( new PaperUserQuery());
        List<PaperUserPapage> collect = paperUserPapages.stream().filter(f -> f.getFraction() != null).collect(Collectors.toList());
        List<PaperUserQuery> queries = new ArrayList<>();
        collect.forEach(f -> {
            queries.add(PaperUserQuery.builder()
                    .classId(f.getClassId())
                    .className(f.getClassName())
                    .paperId(f.getPaperId())
                    .paperName(f.getPaperName())
                    .build())
            ;
        });
        return queries;
    }

    @Override
    public List<PaperExportVO> queryPaperExport(Integer paperId) {
        return paperUserMapper.queryPaperExport(paperId);
    }

    @Override
    public List<AchievementExportVO> queryExport() {
        return paperUserMapper.queryExport();

    }
}
