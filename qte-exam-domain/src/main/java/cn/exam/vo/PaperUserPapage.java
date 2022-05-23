package cn.exam.vo;

import lombok.Data;

/**
 * @author pekosmile
 * @version 1.0
 * @date ##
 */
@Data
public class PaperUserPapage {
    private Integer paperId;
    private String paperName;
    private Integer fraction;
    private Integer difficulty;
    private String userName;
    private String className;
    private Integer paperScore;
    private Integer classId;
}
