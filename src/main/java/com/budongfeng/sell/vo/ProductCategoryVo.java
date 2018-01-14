package com.budongfeng.sell.vo;

import com.budongfeng.sell.dataobject.ProductInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *  商品类目VO
 *  @Author 田培融  canghaihongxin@163.com
 *  @Company 不动峰 www.budongfeng.com
 *  @Date 2017/11/12/012 22:23
 */
@Data
public class ProductCategoryVo implements Serializable {

    private static final long serialVersionUID = 5378201903894817362L;
    @JsonProperty("name")
    private String categoryName;

    @JsonProperty("type")
    private Integer categoryType;

    @JsonProperty("foods")
    private List<ProductInfoVo> productInfos;

}
