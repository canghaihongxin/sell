package com.budongfeng.sell.dataobject;

import com.budongfeng.sell.enums.OrderStatusEnum;
import com.budongfeng.sell.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 *  订单
 *  @Author 田培融  canghaihongxin@163.com
 *  @Company 不动峰 www.budongfeng.com
 *  @Date 2017/11/13/013 15:27
 */

@Entity
@Data
public class OrderMaster {

    /** 订单 id */
    @Id
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
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();

    /**支付状态 , 默认为0未支付*/
    private Integer payStatus= PayStatusEnum.WAIT.getCode();

    /**创建时间*/
    private Date createTime;

    /** 修改时间 */
    private Date updateTime;

}
