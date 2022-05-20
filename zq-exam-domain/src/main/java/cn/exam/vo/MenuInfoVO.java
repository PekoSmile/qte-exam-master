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
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuInfoVO {
    private Integer menuId;

    private String icon;

    private String index;
    private String menuIndex;

    private String title;
    private List<MenuInfoVO> subs;


    private Integer menuDegree;

    private Integer parentId;

}
