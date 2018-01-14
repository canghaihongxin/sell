package com.budongfeng.sell.enums;

import lombok.Getter;

/**
 *  商品状态的枚举
 */
@Getter
public enum ProductStatusEnums implements CodeEnum {

    up(0,"在架"),
    down(1,"下架")
    ;

    private Integer code;

    private String msg;

    ProductStatusEnums(Integer code, String message) {
        this.code = code;
        this.msg = message;
    }

}
