package com.budongfeng.sell.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Id;
import java.math.BigDecimal;

@Data
public class ProductForm {


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

    /* 类目编号 */
    private Integer categoryType;


}
