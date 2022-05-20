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
public class PaperVO {
    private String title;
    private String value;
    private Integer fraction;
    private List<AnswerVO> item;

}
