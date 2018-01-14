package com.budongfeng.sell.service.impl;

import com.budongfeng.sell.dto.OrderDTO;
import com.budongfeng.sell.enums.ResultEnum;
import com.budongfeng.sell.exception.SellException;
import com.budongfeng.sell.service.BuyerService;
import com.budongfeng.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    private OrderService orderService;

    @Override
    public OrderDTO findOrderOne(String openid, String orderid) {
        return  checkOrderOwner(openid,orderid);
    }

    @Override
    public OrderDTO cancelOrder(String openid, String orderid) {

        OrderDTO orderDTO = orderService.findOne(orderid);
        if(orderDTO==null){
            log.error("【取消订单】查不到该订单 orderid={}",orderid);
            throw new  SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        if(!orderDTO.getBuyerOpenid().equals(openid)){
            log.error("【取消订单】 订单的openid不一致  openid={},orderDTO={}",openid,orderDTO);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return orderService.cancel(orderDTO);
    }


    public OrderDTO checkOrderOwner(String openid, String orderid){

        OrderDTO orderDTO = orderService.findOne(orderid);
        if(orderDTO==null){
            return null;
        }
        // 解决越权问题
        if(!orderDTO.getBuyerOpenid().equals(openid)){
            log.error("【查询订单详情】 订单的openid不一致  openid={},orderDTO={}",openid,orderDTO);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }

        return orderDTO;
    }
}
