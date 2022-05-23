package cn.exam.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @version 1.0
 * @date ##
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoVO {
    private Integer id ;
    private String userId;
    private String userName;
    private String roleId;
    private List<String> roleName;
}
