package com.budongfeng.sell.dto;

import com.budongfeng.sell.converter.Date2LongSerializer;
import com.budongfeng.sell.dataobject.OrderDetail;
import com.budongfeng.sell.enums.OrderStatusEnum;
import com.budongfeng.sell.enums.PayStatusEnum;
import com.budongfeng.sell.utils.EnumUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO  {

    /** 订单 id */
    private String orderId;

    /** 买家名字*/
    private String buyerName;

    /** 买家手机号*/
    private String buyerPhone;

    /** 买家地址*/
    private String buyerAddress;

    /** 买家微信Opendid*/
    private String buyerOpenid;

    /** 订单金额*/
    private BigDecimal orderAmount;

    /** 订单状态 , 默认为0新下单 */
    private Integer orderStatus ;

    /**支付状态 , 默认为0未支付*/
    private Integer payStatus;

    /**创建时间*/
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    /** 修改时间 */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    /**订单详情*/
    List<OrderDetail> orderDetailList;

    /** 如果要求返回到页面的直为空是  结果为{}要给一个默认值 */
    /**订单详情*/
    //List<OrderDetail> orderDetailList=new ArrayList<>();


    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum(){
        return EnumUtil.getByCode(orderStatus,OrderStatusEnum.class);
    }

    @JsonIgnore
    public PayStatusEnum getPayStatusEnum(){
        return EnumUtil.getByCode(payStatus,PayStatusEnum.class);
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "orderId='" + orderId + '\'' +
                ", buyerName='" + buyerName + '\'' +
                ", buyerPhone='" + buyerPhone + '\'' +
                ", buyerAddress='" + buyerAddress + '\'' +
                ", buyerOpenid='" + buyerOpenid + '\'' +
                ", orderAmount=" + orderAmount +
                ", orderStatus=" + orderStatus +
                ", payStatus=" + payStatus +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", orderDetailList=" + orderDetailList +
                '}';
    }


}
