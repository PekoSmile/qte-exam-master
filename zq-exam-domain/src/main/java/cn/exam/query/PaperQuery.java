package cn.exam.query;

import lombok.Data;

/**
 * @author pekosmile
 * @version 1.0
 * @date ##
 */
@Data
public class PaperQuery extends BaseQuery {

    private Integer classId;
    private String userId;
}
