package com.budongfeng.sell.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class OrderForm {

    /**用户姓名 */
    @NotEmpty(message = "姓名必填")
    private String name;

    /**手机号*/
    @NotEmpty(message = "手机号必填")
    private String phone;

    /**用户openid*/
    @NotEmpty(message = "用户openid必填")
    private String openid;

    /**用户地址*/
    @NotEmpty(message = "用户地址")
    private String address;

    /**购物车*/
    @NotEmpty
    private String items;
}
