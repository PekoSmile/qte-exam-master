package cn.exam.domain.qte;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @File: QteClassInfo
 * @author pekosmile
 * @date ##
 * @Description:
 */
@Data
@Table(name = "qte_class_info")
public class QteClassInfo implements Serializable {
    /**
     *
     */
    @Id
    @Column(name ="class_id")
    private Integer classId;
    /**
     *
     */
    @Column(name ="class_name")
    private String className;
}
