package cn.exam.so;

import lombok.Data;

/**
 * @version 1.0  注册
 * @date ##
 */
@Data
public class UserInfoSO {
    /**
     * 学号  或 工号
     */
    private String userId;



    private Integer typeId;

    /**
     * 密码
     */
    private String password;

    private String userName;

    private String confirmPassword;

    private String isDelete;

    private Integer classId;





}
