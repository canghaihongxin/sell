package com.budongfeng.sell.repository;

import com.budongfeng.sell.dataobject.SellerInfo;
import com.budongfeng.sell.utils.KeyUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerInfoRepositoryTest {

    @Autowired
    private SellerInfoRepository sellerInfoRepository;

    @Test
    public void save() throws Exception {
        SellerInfo sellerInfo=new SellerInfo();
        sellerInfo.setOpenid("abc");
        sellerInfo.setSellerId(KeyUtil.genUniqueKey());
        sellerInfo.setUsername("admin");
        sellerInfo.setPassword("admin");
        SellerInfo info = sellerInfoRepository.save(sellerInfo);
        Assert.assertNotNull(info);

    }

    @Test
    public void findByOpenid() throws Exception {

        SellerInfo abc = sellerInfoRepository.findByOpenid("abc");
        Assert.assertEquals("abc",abc.getOpenid());
    }

}