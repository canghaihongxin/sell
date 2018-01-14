package com.budongfeng.sell.service;

import com.budongfeng.sell.dto.OrderDTO;

/**
 *  买家service
 *  @Author 田培融  canghaihongxin@163.com
 *  @Company 不动峰 www.budongfeng.com
 *  @Date 2017/11/15/015 14:11
 */
public interface BuyerService {


    OrderDTO findOrderOne(String openid,String orderid);

    OrderDTO cancelOrder(String openid,String orderid);
}
