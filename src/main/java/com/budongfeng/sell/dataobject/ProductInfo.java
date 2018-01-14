package com.budongfeng.sell.dataobject;

import com.budongfeng.sell.enums.ProductStatusEnums;
import com.budongfeng.sell.utils.EnumUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "product_info")
@Entity
@Data
@DynamicUpdate
public class ProductInfo implements Serializable {
    private static final long serialVersionUID = 3696532777640642592L;
    @Id
    //@Column(name = "product_id")
    private String productId;

    /*商品名称 */
    private String productName;

    /*商品价格*/
    private BigDecimal productPrice;

    /*商品库存*/
    private Integer productStock;

    /*商品描述*/
    private String productDescription;

    /*商品图标 */
    private String productIcon;

    /*商品状态 0正常1下架*/
    private Integer productStatus = ProductStatusEnums.up.getCode();

    /* 类目编号 */
    private Integer categoryType;

    private Date createTime;

    private Date updateTime;

    @JsonIgnore
    public ProductStatusEnums getProductStatusEnums(){
        return EnumUtil.getByCode(productStatus,ProductStatusEnums.class);
    }

    public ProductInfo() {
    }

    public ProductInfo(String productId, String productName, BigDecimal productPrice, Integer productStock, String productDescription, String productIcon, Integer productStatus, Integer categoryType) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.productDescription = productDescription;
        this.productIcon = productIcon;
        this.productStatus = productStatus;
        this.categoryType = categoryType;
    }


}
