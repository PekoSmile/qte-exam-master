package cn.exam.domain.qte;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @date ##
 * @Description:
 */
@Data
@Table(name = "qte_user_role")
public class QteUserRole implements Serializable {
    /**
     *
     */
    @Id
    @Column(name ="id")
    private Integer id;
    /**
     *
     */
    @Column(name ="user_id")
    private String userId;
    /**
     *
     */
    @Column(name ="role_id")
    private String roleId;
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
