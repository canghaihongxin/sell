package com.budongfeng.sell.repository;

import com.budongfeng.sell.dataobject.OrderDetail;
import com.budongfeng.sell.dataobject.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository repository;

    @Test
    public  void saveTest(){

        OrderDetail orderDetail=new OrderDetail();

        orderDetail.setDetailId("4885652");
        orderDetail.setOrderId("7896541");
        orderDetail.setProductId("123457");
        orderDetail.setProductIcon("hjttp://sdfsdf");
        orderDetail.setProductName("皮皮虾");
        orderDetail.setProductPrice(new BigDecimal(30.3));
        orderDetail.setProductQuantity(3);
        OrderDetail save = repository.save(orderDetail);
        Assert.assertNotNull(save);

    }
    @Test
    public void findByOrderId() throws Exception {
        List<OrderDetail> byOrderId = repository.findByOrderId("789651");
        Assert.assertNotEquals(0,byOrderId.size());
    }

}