package com.budongfeng.sell.service;

import com.budongfeng.sell.dto.OrderDTO;
import org.springframework.data.domain.jaxb.SpringDataJaxb;

/**
 *  推荐消息
 *  @Author 田培融  canghaihongxin@163.com
 *  @Company 不动峰 www.budongfeng.com
 *  @Date 2017/12/1/001 13:03
 */
public interface PushMessageService {

    /**
     *  订单状态变更消息
      * @param orderDTO
     */
    void orderStatus(OrderDTO orderDTO);
}
