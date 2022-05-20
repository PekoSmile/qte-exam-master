package cn.exam.query;

import lombok.Data;

/**
 * @author pekosmile
 * @version 1.0
 * @date ##
 */
@Data
public class TitlePageQuery extends BaseQuery {
    private String titleName;
    private String subjectName;
    private String className;
    private String titleType;
    private String titleFraction;

}
