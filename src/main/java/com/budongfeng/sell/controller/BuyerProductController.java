package com.budongfeng.sell.controller;

import com.budongfeng.sell.dataobject.ProductCategory;
import com.budongfeng.sell.dataobject.ProductInfo;
import com.budongfeng.sell.enums.ProductStatusEnums;
import com.budongfeng.sell.service.ProductCategoryService;
import com.budongfeng.sell.service.ProductInfoService;
import com.budongfeng.sell.utils.ResultVOUtil;
import com.budongfeng.sell.vo.ProductCategoryVo;
import com.budongfeng.sell.vo.ProductInfoVo;
import com.budongfeng.sell.vo.ResultVo;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private ProductInfoService productInfoService;

    @RequestMapping("/list")
    @Cacheable(cacheNames="product",key = "#sllerId",condition = "#sllerId.length()>3",unless = "#result.getCode() != 0") //spel表达式 当只有符合condition里面的条件时候 才会进行缓存  unless 符合里面的条件 进行缓存
    public ResultVo productList(String sllerId) {

        //查询所有上架商品
        List<ProductInfo> ProductInfoList = productInfoService.findUpAll();
        //查询所有类目
        List<Integer> categoryList = ProductInfoList.stream().map(e -> e.getCategoryType()).collect(Collectors.toList());
        List<ProductCategory> categories = productCategoryService.findByCategoryTypeIn(categoryList);

        //数据拼装

        List<ProductCategoryVo> productCategoryVoList = new ArrayList<>();
        for (ProductCategory category : categories) {
            ProductCategoryVo productCategoryVo = new ProductCategoryVo();
            productCategoryVo.setCategoryName(category.getCategoryName());
            productCategoryVo.setCategoryType(category.getCategoryType());

            List<ProductInfoVo> productInfoVolist=new ArrayList<>();
            for (ProductInfo info:ProductInfoList) {
                ProductInfoVo productInfoVo=new ProductInfoVo();
                if(category.getCategoryType().equals(info.getCategoryType())){
                    BeanUtils.copyProperties(info, productInfoVo);
                    productInfoVolist.add(productInfoVo);
                }
            }
            productCategoryVo.setProductInfos(productInfoVolist);
            productCategoryVoList.add(productCategoryVo);
        }
        return ResultVOUtil.success(productCategoryVoList);
    }

}
