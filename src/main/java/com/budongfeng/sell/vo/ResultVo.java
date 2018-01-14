package com.budongfeng.sell.vo;

import lombok.Data;

import java.io.Serializable;

/**
 *  最终返回给页面的vo
 *  @Author 田培融  canghaihongxin@163.com
 *  @Company 不动峰 www.budongfeng.com
 *  @Date 2017/11/12/012 22:16
 */
@Data
public class ResultVo<T> implements Serializable {
    private static final long serialVersionUID = 358418296822440813L;
    /*状态码*/
    private Integer code;

    /*提示信息*/
    private String msg;

    private T data;
}
