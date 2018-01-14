package com.budongfeng.sell.service.impl;

import com.budongfeng.sell.dataobject.OrderDetail;
import com.budongfeng.sell.dto.OrderDTO;
import com.budongfeng.sell.enums.OrderStatusEnum;
import com.budongfeng.sell.enums.PayStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl orderService;

    private  String openid="1101101";

    private  final  String orderid="15105811798638435678";
    @Test
    public void create() throws Exception {

        OrderDTO orderDTO=new OrderDTO();
        orderDTO.setBuyerName("李聪聪");
        orderDTO.setBuyerAddress("陕师大");
        orderDTO.setBuyerPhone("1234567984653");
        orderDTO.setBuyerOpenid(openid);


        List<OrderDetail> details=new ArrayList<>();
        OrderDetail o1=new OrderDetail();
        o1.setProductId("123479");
        o1.setProductQuantity(1);
        OrderDetail o2=new OrderDetail();
        o2.setProductId("123457");
        o2.setProductQuantity(2);
        details.add(o1);
        details.add(o2);
        orderDTO.setOrderDetailList(details);
        OrderDTO result = orderService.create(orderDTO);
        log.info(result.toString());

    }


    @Test
    public void findOne() throws Exception {
        OrderDTO one = orderService.findOne(orderid);
        Assert.assertNotNull(one);
    }

    @Test
    public void findList() throws Exception {
        PageRequest pageRequest=new PageRequest(0,3);
        Page<OrderDTO> list = orderService.findList(openid, pageRequest);
        Assert.assertNotEquals(0,list.getTotalElements());
    }

    @Test
    public void cancel() throws Exception {
        OrderDTO orderDTO = orderService.findOne(orderid);
        OrderDTO result = orderService.cancel(orderDTO);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(),result.getOrderStatus());
    }

    @Test
    public void finish() throws Exception {
        OrderDTO orderDTO = orderService.findOne(orderid);
        OrderDTO result = orderService.finish(orderDTO);
        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(),result.getOrderStatus());
    }

    @Test
    public void paid() throws Exception {
        OrderDTO orderDTO = orderService.findOne(orderid);
        OrderDTO result = orderService.paid(orderDTO);
        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(),result.getPayStatus());
    }

    @Test
    public void list(){
        PageRequest pageRequest=new PageRequest(0,3);
        Page<OrderDTO> list = orderService.findList(pageRequest);
        Assert.assertTrue("查询所有订单列表",list.getTotalElements()>0);
    }

}