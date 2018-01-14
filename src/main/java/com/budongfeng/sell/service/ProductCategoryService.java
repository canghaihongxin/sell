package com.budongfeng.sell.service;


import com.budongfeng.sell.dataobject.ProductCategory;

import java.util.List;

public interface ProductCategoryService {

    ProductCategory findOne(Integer id);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> list);

    ProductCategory save(ProductCategory productCategory);

}
