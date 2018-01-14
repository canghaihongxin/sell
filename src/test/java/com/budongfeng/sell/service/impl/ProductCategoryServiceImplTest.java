package com.budongfeng.sell.service.impl;

import com.budongfeng.sell.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryServiceImplTest {

    @Autowired
    private ProductCategoryServiceImpl productCategoryService;

    @Test
    public void findOne() throws Exception {
        ProductCategory one = productCategoryService.findOne(2);
        Assert.assertEquals(new Integer(4),one.getCategoryType());
    }

    @Test
    public void findAll() throws Exception {
        List<ProductCategory> all = productCategoryService.findAll();
        Assert.assertNotNull(all);
    }

    @Test
    public void findByCategoryTypeIn() throws Exception {

        List<Integer> list= Arrays.asList(2,3);
        List<ProductCategory> byCategoryTypeIn = productCategoryService.findByCategoryTypeIn(list);
        Assert.assertNotEquals(0,byCategoryTypeIn.size());
    }

    @Test
    public void save() throws Exception {
        ProductCategory productCategory=new ProductCategory("限时抢购",5);
        ProductCategory save = productCategoryService.save(productCategory);
        Assert.assertNotNull(save);

    }

}