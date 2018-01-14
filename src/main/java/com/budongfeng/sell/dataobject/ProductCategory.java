package com.budongfeng.sell.dataobject;


import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "product_category")  //可以不加 命名方式一样就可以
@Entity
@DynamicUpdate  //动态更新，  更新时间的必须添加
@Data
public class ProductCategory {

    /*类目id*/
    @Id
    @GeneratedValue
    private Integer categoryId;

    /*类目名称*/
    private String categoryName;

    /*类目编号*/
    private Integer categoryType;

    private Date createTime;

    private Date updateTime;

    public ProductCategory() {
    }

    public ProductCategory(String categoryName, Integer categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }
}
