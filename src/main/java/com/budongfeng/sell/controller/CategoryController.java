package com.budongfeng.sell.controller;

import com.budongfeng.sell.config.CommonConfig;
import com.budongfeng.sell.dataobject.ProductCategory;
import com.budongfeng.sell.exception.SellException;
import com.budongfeng.sell.form.CategoryForm;
import com.budongfeng.sell.service.ProductCategoryService;
import com.lly835.bestpay.rest.type.Post;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/seller/category")
public class CategoryController {

    @Autowired
    private CommonConfig commonConfig;

    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping("/list")
    public String list(ModelMap map){

        List<ProductCategory> productCategoryList = productCategoryService.findAll();
        map.put("systemName",commonConfig.getContext_path());
        map.put("productCategoryList",productCategoryList);
        return "category/list";
    }

    @GetMapping("/index")
    public String index(@RequestParam(name = "categoryId",required = false) Integer categoryId,
                        ModelMap map){

        map.put("systemName",commonConfig.getContext_path());
        ProductCategory productCategory=new ProductCategory();
        // 1.如果不为空 则为修改
        if(!StringUtils.isEmpty(categoryId)){
             productCategory = productCategoryService.findOne(categoryId);
        }
        //2.将数据回显
        map.put("productCategory",productCategory);
        return "/category/index";
    }


    /**
     *  保存或者修改类目
     * @param categoryForm
     * @param bindingResult
     * @param map
     * @return
     */
    @PostMapping("/save")
    public String catSaveOrUpdate(@Valid CategoryForm categoryForm, BindingResult bindingResult,
                                  ModelMap map){

        map.put("systemName",commonConfig.getContext_path());

        if(bindingResult.hasErrors()){
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            map.put("url",commonConfig.getContext_path()+"/seller/category/list");
            map.put("systemName",commonConfig.getContext_path());
            return "common/error";
        }
        ProductCategory category = new ProductCategory();
        try {
            if(!StringUtils.isEmpty(category.getCategoryId())){
                category = productCategoryService.findOne(categoryForm.getCategoryId());
            }
            BeanUtils.copyProperties(categoryForm,category);
            productCategoryService.save(category);
            map.put("url",commonConfig.getContext_path()+"/seller/category/list");
        } catch (SellException e) {
            map.put("msg",e.getMessage());
            map.put("url",commonConfig.getContext_path()+"/seller/category/list");
            map.put("systemName",commonConfig.getContext_path());
            return "common/error";
        }
        return "common/success";
    }

}
