package com.budongfeng.sell.repository;

import com.budongfeng.sell.dataobject.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {


    @Autowired
    private OrderMasterRepository repository;

    private final String opendId="110110";
    @Test
    public void saveTest(){
        OrderMaster orderMaster=new OrderMaster();
        orderMaster.setBuyerOpenid(opendId);
        orderMaster.setBuyerName("田培融");
        orderMaster.setBuyerAddress("草滩 路");
        orderMaster.setBuyerPhone("12345678901");
        orderMaster.setOrderId("7896541");
        orderMaster.setOrderAmount(new BigDecimal(10.3));
        OrderMaster save = repository.save(orderMaster);

        Assert.assertNotNull(save);
    }
    @Test
    public void findByBuyerOpenid() throws Exception {

        PageRequest pageRequest=new PageRequest(0,3);
        Page<OrderMaster> page = repository.findByBuyerOpenid(opendId, pageRequest);
        Assert.assertNotEquals(0,page.getTotalElements());


    }

}