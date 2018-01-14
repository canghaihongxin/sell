package com.budongfeng.sell.repository;

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
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository repository;

    @Test
    public  void findOne(){
        ProductCategory one = repository.findOne(1);
        System.out.println(one);
    }

    @Test
    public void saveOne(){

        ProductCategory productCategory=new ProductCategory("女生最爱",3);
        ProductCategory result = repository.save(productCategory);
        Assert.assertNotNull(result);

    }

    @Test
    public void update(){
       /*  ProductCategory productCategory=new ProductCategory();
       productCategory.setCategoryId(2);
        productCategory.setCategoryName("男生最爱");
        productCategory.setCategoryType(3);*/

        ProductCategory category = repository.findOne(2);
        category.setCategoryType(3);
        repository.save(category);
    }


    @Test
    public void findByCategoryTypeIn(){

        List<Integer> list  = Arrays.asList(2,3);
        List<ProductCategory> result = repository.findByCategoryTypeIn(list);

        Assert.assertNotEquals(0,result.size());


    }
}