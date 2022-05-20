package cn.exam.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author pekosmile
 * @version 1.0
 * @date ##
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleMenuVO {
    private Integer id;

    private String label;

    private Integer status;

    private String roleId;


    private List<RoleMenuVO> children;

}
