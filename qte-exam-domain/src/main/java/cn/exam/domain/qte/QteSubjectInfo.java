package cn.exam.domain.qte;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @File: QteSubjectInfo
 * @author pekosmile
 * @date ##
 * @Description:
 */
@Data
@Table(name = "qte_subject_info")
@AllArgsConstructor
@NoArgsConstructor
public class QteSubjectInfo implements Serializable {
    /**
     * 科目id
     */
    @Id
    @Column(name ="subject_id")
    private Integer subjectId;
    /**
     * 科目名称
     */
    @Column(name ="subject_name")
    private String subjectName;
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
