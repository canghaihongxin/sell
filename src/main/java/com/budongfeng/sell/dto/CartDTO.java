package com.budongfeng.sell.dto;

import lombok.Data;

/**
 *  购物车DTO
 *  @Author 田培融  canghaihongxin@163.com
 *  @Company 不动峰 www.budongfeng.com
 *  @Date 2017/11/13/013 20:07
 */
@Data
public class CartDTO
{
    private String  productId;
    private Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
