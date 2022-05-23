package cn.exam.domain.qte;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @File: QteSubjectUserLink
 * @author pekosmile
 * @date ##
 * @Description:
 */
@Data
@Table(name = "qte_subject_user_link")
public class QteSubjectUserLink implements Serializable {
    /**
     *
     */
    @Id
    @Column(name ="id")
    private Integer id;
    /**
     *
     */
    @Column(name ="class_id")
    private Integer classId;
    /**
     *
     */
    @Column(name ="paper_id")
    private Integer paperId;
    /**
     *
     */
    @Column(name ="title_id")
    private Integer titleId;
    /**
     *
     */
    @Column(name ="create_time")
    private String createTime;
    /**
     *
     */
    @Column(name ="update_time")
    private String updateTime;
}
