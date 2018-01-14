package com.budongfeng.sell.repository;

import com.budongfeng.sell.dataobject.ProductInfo;
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
public class ProductInfoRepositoryTest {

    @Autowired
    private ProductInfoRepository repository;

    @Test
    public void save(){

        ProductInfo productInfo=new ProductInfo("123456","皮蛋粥",new BigDecimal(3.2),100,"很好喝","http://xxxxxx.jpg",0,3);
        ProductInfo save = repository.save(productInfo);
        Assert.assertNotNull(save);
    }

    @Test
    public void findByProductStatus() throws Exception {
        List<ProductInfo> result = repository.findByProductStatus(0);
        Assert.assertNotNull(result);
    }

}