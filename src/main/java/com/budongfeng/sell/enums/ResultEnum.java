package com.budongfeng.sell.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {

    PARAM_ERROR(1,"参数不正确"),
    PRODUCT_NOT_EXIST(10,"商品不存在"),
    PRODUCT_STOCK_ERROR(11,"商品库存异常"),
    PRODUCT_STATUS_ERROR(24,"商品状态不正确"),


    ORDER_NOT_EXIST(12,"订单不存在"),
    ORDERDETAIL_NOT_EXIST(13,"订单详情不存在"),
    ORDER_STATUS_ERROR(14,"订单状态异常"),
    ORDER_UPDATE_ERROR(15,"订单修改失败"),
    ORDER_DETAIL_EMPTY(16,"订单详情为空"),
    ORDER_FINISHED_ERROR(17,"订单完结失败"),
    ORDER_PAY_ERROR(18,"订单支付状态修改失败"),
    ORDER_CREATE_ERROR(19,"订单创建失败"),

    ORDER_OWNER_ERROR(19,"该订单不属于当前用户"),

    WECHAT_MP_ERROR(20,"微信公众账号方面错误"),

    WXPAY_NOITFY_MONEY_VERIFY_ERROR(21,"微信支付异步通知金额校验不通过"),

    ORDER_CANCEL_CUCCESS(22,"取消订单成功"),
    ORDER_FINISH_CUCCESS(23,"完结订单成功"),

    USERNAME_NOT_EXIST(24,"用户名不存在"),
    SELL_LOGIN_ERROR(25,"登录失败，信息不正确"),
    SELLER_LOGOUT_SUCCESS(26,"用户退出成功"),
    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
