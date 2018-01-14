package com.budongfeng.sell.service.impl;

import com.budongfeng.sell.dataobject.ProductInfo;
import com.budongfeng.sell.dto.CartDTO;
import com.budongfeng.sell.enums.ProductStatusEnums;
import com.budongfeng.sell.enums.ResultEnum;
import com.budongfeng.sell.exception.SellException;
import com.budongfeng.sell.repository.ProductInfoRepository;
import com.budongfeng.sell.service.ProductInfoService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@CacheConfig(cacheNames = "product")
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    private ProductInfoRepository repository;

    @Override
    @Cacheable(key = "123")
    public ProductInfo findOne(String productId) {
        return repository.findOne(productId);
    }

    /*查询所有上架的商品信息*/
    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnums.up.getCode());
    }


    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    @CachePut(key = "123") //key 默认是方法的形参，一般不采用默认值
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    /**
     *  加库存
     *  @Author 田培融  canghaihongxin@163.com
     *  @Company 不动峰 www.budongfeng.com
     *  @Date 2017/11/14/014 13:45
     */
    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {

        for (CartDTO cartdto : cartDTOList) {
            ProductInfo productInfo = repository.findOne(cartdto.getProductId());
            if(productInfo==null){
                log.error("加库存时商品不存在");
                throw new  SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result=productInfo.getProductStock()+cartdto.getProductQuantity();
            productInfo.setProductStock(result);
            repository.save(productInfo);
        }
    }

    /**
     * 减库存
     *
     * @Author 田培融  canghaihongxin@163.com
     * @Company 不动峰 www.budongfeng.com
     * @Date 2017/11/13/013 20:24
     */
    @Override
    @Transactional
    public void descreaseStock(List<CartDTO> cartDTOList) {

        for (CartDTO cartDTO : cartDTOList) {
            ProductInfo productInfo = repository.findOne(cartDTO.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            /** 使用redis锁机制来防止超卖*/
            //判断库存数量异常
            Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if (result < 0) {
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(result);
            repository.save(productInfo);
        }
    }

    /**
     * 上架
     * @param orderId
     * @return
     */
    @Override
    public ProductInfo onSale(String orderId) {
        //1.校验
        ProductInfo productInfo = repository.findOne(orderId);
        if(productInfo==null){
            log.error("【商品上架】商品不存在");
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if(productInfo.getProductStatus().equals(ProductStatusEnums.up.getCode())){
            log.error("【商品上架】商品状态不正确productinfo={}",productInfo);
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        //2.执行
        productInfo.setProductStatus(ProductStatusEnums.up.getCode());
        return repository.save(productInfo);
    }

    /***
     * 下架
     * @param orderId
     * @return
     */
    @Override
    public ProductInfo offSale(String orderId) {
        //1.校验
        ProductInfo productInfo = repository.findOne(orderId);
        if(productInfo==null){
            log.error("【商品上架】商品不存在");
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if(productInfo.getProductStatus().equals(ProductStatusEnums.down.getCode())){
            log.error("【商品上架】商品状态不正确productinfo={}",productInfo);
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        //2.执行
        productInfo.setProductStatus(ProductStatusEnums.down.getCode());
        return repository.save(productInfo);
    }
}
