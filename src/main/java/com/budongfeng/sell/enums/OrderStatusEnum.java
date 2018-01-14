package com.budongfeng.sell.enums;

import com.sun.org.apache.bcel.internal.generic.NEW;
import lombok.Getter;

import java.io.Serializable;

/**
 *  订单状态
 *  @Author 田培融  canghaihongxin@163.com
 *  @Company 不动峰 www.budongfeng.com
 *  @Date 2017/11/13/013 15:33
 */
@Getter
public enum  OrderStatusEnum implements Serializable,CodeEnum {

    NEW(0,"新定单"),
    FINISHED(1,"完结"),
    CANCEL(2,"取消")
    ;
    private Integer code;

    private String msg;

    OrderStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
