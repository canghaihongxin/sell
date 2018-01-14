package com.budongfeng.sell.service.impl;

import com.budongfeng.sell.dataobject.SellerInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerInfoServiceImplTest {

    private static  final  String opendid="abc";
    private static  final  String username="admin";
    @Autowired
    private SellerInfoServiceImpl sellerInfoService;

    @Test
    public void findByUsername() throws Exception {

        SellerInfo byUsername = sellerInfoService.findByUsername(username);
        Assert.assertEquals(username,byUsername.getUsername());
    }





    @Test
    public void save() throws Exception {

    }

    @Test
    public void findByOpenid() throws Exception {

        SellerInfo sellerInfo = sellerInfoService.findByOpenid(opendid);
        Assert.assertEquals(opendid,sellerInfo.getOpenid());

    }

}