package com.budongfeng.sell.service.impl;

import com.budongfeng.sell.dataobject.ProductInfo;
import com.budongfeng.sell.enums.ProductStatusEnums;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoServiceImplTest {

    @Autowired
    private ProductInfoServiceImpl productInfoService;


    @Test
    public void onSale() throws Exception {
        ProductInfo productInfo = productInfoService.onSale("123456");
        Assert.assertEquals(ProductStatusEnums.up,productInfo.getProductStatusEnums());
    }

    @Test
    public void offSale() throws Exception {

        ProductInfo productInfo = productInfoService.offSale("123456");
        Assert.assertEquals(ProductStatusEnums.down,productInfo.getProductStatusEnums());
    }


    @Test
    public void findOne() throws Exception {
        ProductInfo one = productInfoService.findOne("123456");
        Assert.assertEquals("123456",one.getProductId());
    }

    @Test
    public void findAll() throws Exception {
        List<ProductInfo> all = productInfoService.findUpAll();
        Assert.assertNotEquals(0,all.size());

    }

    @Test
    public void findAll1() throws Exception {
        PageRequest pageRequest=new PageRequest(0,2);
        Page<ProductInfo> all = productInfoService.findAll(pageRequest);
        Assert.assertNotEquals(0,all.getTotalElements());
    }

    @Test
    public void save() throws Exception {
        ProductInfo productInfo=new ProductInfo("123457","皮皮虾",new BigDecimal(10.3),100,"很好吃","http://xxxxxx.jpg",ProductStatusEnums.up.getCode(),3);
        ProductInfo save = productInfoService.save(productInfo);
        Assert.assertNotNull(save);

    }

}