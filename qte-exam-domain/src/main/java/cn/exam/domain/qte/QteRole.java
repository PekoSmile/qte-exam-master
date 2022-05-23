package cn.exam.domain.qte;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @File: QteRole
 * @author pekosmile
 * @date ##
 * @Description:
 */
@Data
@Table(name = "qte_role")
public class QteRole implements Serializable {
    /**
     *
     */
    @Id
    @Column(name ="id")
    private Integer id;
    /**
     * 角色id
     */
    @Column(name ="role_id")
    private String roleId;
    /**
     *
     */
    @Column(name ="role_name")
    private String roleName;
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
