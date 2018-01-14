package com.budongfeng.sell.service.impl;

import com.budongfeng.sell.dto.OrderDTO;
import com.budongfeng.sell.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PushMessageServiceImplTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PushMessageServiceImpl pushMessageService;

    @Test
    public void orderStatus() throws Exception {

        OrderDTO orderDTO = orderService.findOne("15112506474202355424");

        pushMessageService.orderStatus(orderDTO);
    }

}