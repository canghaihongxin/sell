package com.budongfeng.sell.service.impl;

import com.budongfeng.sell.dto.OrderDTO;
import com.budongfeng.sell.service.OrderService;
import com.budongfeng.sell.service.PayService;
import com.lly835.bestpay.model.RefundResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PayServiceImplTest {

    @Autowired
    private PayService payService;

    @Autowired
    private OrderService orderService;

    @Test
    public void create() throws Exception {
        OrderDTO orderDTO = orderService.findOne("15106651120664691526");
        payService.create(orderDTO);
    }

    @Test
    public void refund(){
        OrderDTO one = orderService.findOne("15112520018749560617");
        RefundResponse refund = payService.refund(one);

    }
}