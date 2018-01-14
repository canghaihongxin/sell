package com.budongfeng.sell.enums;

import lombok.Getter;

/**
 *  支付状态
 *  @Author 田培融  canghaihongxin@163.com
 *  @Company 不动峰 www.budongfeng.com
 *  @Date 2017/11/13/013 15:39
 */
@Getter
public enum PayStatusEnum implements CodeEnum {

    WAIT(0,"等待支付"),
    SUCCESS(1,"支付成功")
    ;

    private Integer code;
    private String msg;

    PayStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}


