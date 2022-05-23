package cn.exam.domain.qte;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @File: QtePaperUser
 * @author pekosmile
 * @date ##
 * @Description:
 */
@Data
@Table(name = "qte_paper_user")
public class QtePaperUser implements Serializable {
    /**
     *
     */
    @Id
    @Column(name ="id")
    private Integer id;
    /**
     * 试卷id
     */
    @Column(name ="paper_id")
    private Integer paperId;
    /**
     * 考试人
     */
    @Column(name ="user_id")
    private String userId;
    /**
     * 0未考试 1考试完成
     */
    @Column(name ="status")
    private Integer status;
    /**
     * 分数
     */
    @Column(name ="fraction")
    private Integer fraction;
}
