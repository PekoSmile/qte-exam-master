package cn.exam.vo;

import cn.exam.domain.zj.ZjUserInfo;
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
public class UserMenuInfoVO {
    private UserVO user;
//
    private List<MenuInfoVO> menuList;
}
