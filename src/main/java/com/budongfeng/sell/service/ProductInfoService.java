package com.budongfeng.sell.service;

import com.budongfeng.sell.dataobject.ProductInfo;
import com.budongfeng.sell.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductInfoService {

    public ProductInfo findOne(String productId);

    /**
     *  查询所有上架商品
     *  @Author 田培融  canghaihongxin@163.com
     *  @Company 不动峰 www.budongfeng.com
     *  @Date 2017/11/12/012 18:38
     */
   public List<ProductInfo> findUpAll();

    public Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    /**加库存*/
    void increaseStock(List<CartDTO> cartDTOList);

    /**减库存*/
    void descreaseStock(List<CartDTO> cartDTOList);

    /** 上架 */
    ProductInfo onSale(String orderId);

    /**下架*/
    ProductInfo offSale(String orderId);

}
