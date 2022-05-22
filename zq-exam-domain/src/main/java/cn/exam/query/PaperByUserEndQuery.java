package cn.exam.query;

import lombok.Data;

@Data
public class PaperByUserEndQuery extends BaseQuery{
    private String userId;
    private String userName;
    private String paperName;
}
