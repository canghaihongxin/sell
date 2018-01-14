package com.budongfeng.sell.controller;

import com.budongfeng.sell.config.CommonConfig;
import com.budongfeng.sell.dataobject.ProductCategory;
import com.budongfeng.sell.dataobject.ProductInfo;
import com.budongfeng.sell.exception.SellException;
import com.budongfeng.sell.form.ProductForm;
import com.budongfeng.sell.service.ProductCategoryService;
import com.budongfeng.sell.service.ProductInfoService;
import com.budongfeng.sell.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

/**
 *  卖家端商品信息
 *  @Author 田培融  canghaihongxin@163.com
 *  @Company 不动峰 www.budongfeng.com
 *  @Date 2017/11/27/027 21:33
 */
@Controller
@RequestMapping("/seller/product")
public class SellerProductController {

    @Autowired
    private CommonConfig commonConfig;

    @Autowired
    private ProductInfoService productInfoService;
    
    @Autowired
    private ProductCategoryService productCategoryService;

    @RequestMapping("/list")
    public String list(@RequestParam(name = "page",defaultValue = "1") Integer page,
                       @RequestParam(name = "size",defaultValue = "10") Integer size,
                       ModelMap map){
        PageRequest pageRequest=new PageRequest(page-1,size);
        Page<ProductInfo> productInfos = productInfoService.findAll(pageRequest);
        // orderDTOPage.getTotalElements();  //获取总记录数量
        // orderDTOPage.getTotalPages();   //总页数
        map.put("productInfos",productInfos);
        map.put("currentPage",page);
        map.put("size",size);
        map.put("systemName", commonConfig.getContext_path());
        return "product/list";
    }

    @GetMapping("/on_sale")
    public String on_sale(@RequestParam(name = "orderId") String orderId,
                          ModelMap map){
        try {
            productInfoService.onSale(orderId);
        } catch (SellException e) {
            map.put("msg",e.getMessage());
            map.put("url",commonConfig.getContext_path()+"/seller/product/list");
            map.put("systemName",commonConfig.getContext_path());
            return "common/error";
        }
        map.put("url",commonConfig.getContext_path()+"/seller/product/list");
        map.put("systemName",commonConfig.getContext_path());
        return "common/success";
    }

    @GetMapping("/off_sale")
    public String off_sale(@RequestParam(name = "orderId") String orderId,
                          ModelMap map){
        try {
            productInfoService.offSale(orderId);
        } catch (SellException e) {
            map.put("msg",e.getMessage());
            map.put("url",commonConfig.getContext_path()+"/seller/product/list");
            map.put("systemName",commonConfig.getContext_path());
            return "common/error";
        }
        map.put("url",commonConfig.getContext_path()+"/seller/product/list");
        map.put("systemName",commonConfig.getContext_path());
        return "common/success";
    }


    /**
     *  商品添加或者修改页面
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("/index")
    public String productAddOrUpdate(@RequestParam(name = "orderId",required = false) String orderId,
                                     ModelMap map){
        map.put("systemName",commonConfig.getContext_path());
        ProductInfo productInfo = null;
        if(orderId !=null && !orderId.equals("")){
            try {
                productInfo= productInfoService.findOne(orderId);
            } catch (SellException e) {
                map.put("msg",e.getMessage());
                map.put("url",commonConfig.getContext_path()+"/seller/product/list");
                return "common/error";
            }
        }
        List<ProductCategory> categoryList = productCategoryService.findAll();
        map.put("categoryList",categoryList);
        map.put("productInfo",productInfo);
        return "product/index";
    }

    /**
     *  保存 或者 修改商品信息
     * @param productForm
     * @param bindingResult
     * @param map
     * @return
     */
    @PostMapping("/save")
   // @CachePut(cacheNames="product",key = "123")    // 修改redis缓存
   // @CacheEvict(cacheNames="product",key = "123")  // 清除redis缓存
    public String save(@Valid ProductForm productForm,
                       BindingResult bindingResult,
                       ModelMap map){
        map.put("systemName",commonConfig.getContext_path());
        ProductInfo productInfo = new ProductInfo();
        try {
            if(!StringUtils.isEmpty(productForm.getProductId())){
                productInfo =   productInfoService.findOne(productForm.getProductId());
            }else{
                productForm.setProductId(KeyUtil.genUniqueKey());
            }
            BeanUtils.copyProperties(productForm,productInfo);
            productInfoService.save(productInfo);
        } catch (SellException e) {
            map.put("msg",e.getMessage());
            map.put("url",commonConfig.getContext_path()+"/seller/product/list");
            return "common/error";
        }


        map.put("url",commonConfig.getContext_path()+"/seller/product/list");
        return "common/success";
    }

}
