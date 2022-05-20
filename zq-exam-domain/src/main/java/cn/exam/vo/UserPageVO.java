package cn.exam.vo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * @author pekosmile
 * @version 1.0
 * @date ##
 */
@Data
public class UserPageVO {
    /**
     * 用户id
     */
    private String userId;
    /**
     * 密码MD5
     */
    private String password;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 是否删除 0 否  1是
     */
    private Integer isDelete;
    private Integer classId;
    private String  className;
    /**
     * 开始时间
     */
    private String createTime;
    /**
     * 结束时间
     */
    private String updateTime;
    private Integer typeId;

}
