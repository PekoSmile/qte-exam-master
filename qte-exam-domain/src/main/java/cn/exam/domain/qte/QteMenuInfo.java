package cn.exam.domain.qte;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @File: QteMenuInfo
 * @author pekosmile
 * @date ##
 * @Description:
 */
@Data
@Table(name = "qte_menu_info")
public class QteMenuInfo implements Serializable {
    /**
     * 菜单ID
     */
    @Id
    @Column(name ="menu_id")
    private Integer menuId;
    /**
     *菜单名称
     */
    @Column(name ="menu_name")
    private String menuName;
    /**
     *菜单图标
     */
    @Column(name ="menu_icon")
    private String menuIcon;
    /**
     * 菜单链接
     */
    @Column(name ="menu_index")
    private String menuIndex;
    /**
     * 层级 0 1(只放两级层级)
     */
    @Column(name ="menu_degree")
    private Integer menuDegree;
    /**
     * 父级id
     */
    @Column(name ="parent_id")
    private Integer parentId;
    /**
     * 0关闭 1启用
     */
    @Column(name ="menu_status")
    private Integer menuStatus;
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
