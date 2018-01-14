package com.budongfeng.sell.form;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
public class CategoryForm {

    /*类目id*/
    private Integer categoryId;

    /*类目名称*/
    private String categoryName;

    /*类目编号*/
    private Integer categoryType;


}
