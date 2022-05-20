package cn.exam.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author pekosmile
 * @version 1.0
 * @date ##
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnswerVO {
    private String label;
    private String value;
}
