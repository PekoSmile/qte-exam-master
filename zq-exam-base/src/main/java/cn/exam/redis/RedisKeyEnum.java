package cn.exam.redis;

import lombok.Getter;

/**
 * @File: RedisKeyEnum
 * @Date: ##
 * @Description: 用户redis  key
 */
@Getter
public enum RedisKeyEnum {
    USER("LOGIN_USER_INFO","用户信息"),
    REASON("REASON","原因信息");
    private String code;
    private String desc;
    RedisKeyEnum(String code, String desc){
        this.code = code;
        this.desc = desc;
    }
}
