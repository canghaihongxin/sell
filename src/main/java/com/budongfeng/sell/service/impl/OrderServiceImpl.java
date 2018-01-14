package com.budongfeng.sell.service.impl;

import com.budongfeng.sell.converter.OrderMaster2OrderDTO;
import com.budongfeng.sell.dataobject.OrderDetail;
import com.budongfeng.sell.dataobject.OrderMaster;
import com.budongfeng.sell.dataobject.ProductInfo;
import com.budongfeng.sell.dto.CartDTO;
import com.budongfeng.sell.dto.OrderDTO;
import com.budongfeng.sell.enums.OrderStatusEnum;
import com.budongfeng.sell.enums.PayStatusEnum;
import com.budongfeng.sell.enums.ResultEnum;
import com.budongfeng.sell.exception.ResponseBankException;
import com.budongfeng.sell.exception.SellException;
import com.budongfeng.sell.repository.OrderDetailRepository;
import com.budongfeng.sell.repository.OrderMasterRepository;
import com.budongfeng.sell.service.*;
import com.budongfeng.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.xml.transform.Result;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {


    @Autowired
    private PushMessageService pushMessageService;

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private PayService payService;

    @Autowired
    private WebSocket webSocket;

    /**
     * 创建订单
     *
     * @Author 田培融  canghaihongxin@163.com
     * @Company 不动峰 www.budongfeng.com
     * @Date 2017/11/13/013 18:26
     */
    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {

        BigDecimal orderAmount =new BigDecimal(BigInteger.ZERO);

         String orderId= KeyUtil.genUniqueKey();
        //1.查询商品
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            ProductInfo productInfo = productInfoService.findOne(orderDetail.getProductId());
            if (productInfo==null){
               // throw new ResponseBankException(); 改变返回的状态码
                throw  new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //计算订单价格
            orderAmount= productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);
            //2订单详情入库
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetailRepository.save(orderDetail);
        }
        //3.创建订单
        OrderMaster orderMaster=new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
         orderMasterRepository.save(orderMaster);
        //4.减库存
        List<CartDTO> cartDTOS = orderDTO.getOrderDetailList().stream().map(e ->
                new CartDTO(e.getProductId(), e.getProductQuantity())
        ).collect(Collectors.toList());
        productInfoService.descreaseStock(cartDTOS);

        // 发送websocket消息
        webSocket.sendMessage(orderDTO.getOrderId());
        return orderDTO;
    }

    /**
     *  查询订单
     *  @Author 田培融  canghaihongxin@163.com
     *  @Company 不动峰 www.budongfeng.com
     *  @Date 2017/11/14/014 9:15
     */
    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        if (orderMaster==null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if(CollectionUtils.isEmpty(orderDetailList)){
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO=new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    /**
     * 查询所有订单，前台系统 有opendId
     * @param buyerOpenid
     * @param pageable
     * @return
     */
    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasters = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);

        List<OrderDTO> orderDTOList = OrderMaster2OrderDTO.convert(orderMasters.getContent());
        return new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasters.getTotalElements());
    }

    /**
     *  取消订单
     *  @Author 田培融  canghaihongxin@163.com
     *  @Company 不动峰 www.budongfeng.com
     *  @Date 2017/11/14/014 12:12
     */
    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        //判断订单状态
        OrderMaster orderMaster = orderMasterRepository.findOne(orderDTO.getOrderId());
        if(orderMaster==null){
            log.error("【取消订单】订单不存在");
            throw  new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        //修改订单
        if(!orderMaster.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【取消订单】订单状态不正确,orderid={} ,orderStatus={}" ,orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw  new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        orderMaster.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if(updateResult==null){
            log.error("【取消订单】订单更新失败， orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_ERROR);
        }
        BeanUtils.copyProperties(orderMaster,orderDTO);

        //加库存
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【取消订单】订单中无商品详情，orderDTO={}",orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<OrderDetail> orderDetailList = orderDTO.getOrderDetailList();
        List<CartDTO> cartDTOList = orderDetailList.stream().map(e -> new CartDTO(e.getProductId(), e.getProductQuantity())).collect(Collectors.toList());
        productInfoService.increaseStock(cartDTOList);
        //如果已支付，需要退款
        if(orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())){
            payService.refund(orderDTO);
        }
        return orderDTO;
    }

    /**
     *  完结订单
     *  @Author 田培融  canghaihongxin@163.com
     *  @Company 不动峰 www.budongfeng.com
     *  @Date 2017/11/14/014 14:41
     */
    @Override
    public OrderDTO finish(OrderDTO orderDTO) {

        //校验
        OrderMaster orderMaster = orderMasterRepository.findOne(orderDTO.getOrderId());
        if(orderMaster==null){
            log.error("【完结订单】订单不存在");
            throw  new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        if(!OrderStatusEnum.NEW.getCode().equals(orderMaster.getOrderStatus())){
             log.error("【完结订单】 订单状态异常  orderid={}, roderstatus={}",orderMaster.getOrderId(),orderMaster.getOrderStatus());
            throw  new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //执行
        orderMaster.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster master = orderMasterRepository.save(orderMaster);
        if(master==null){
            log.error("【完结订单】 订单完结失败 orderid={}",orderDTO.getOrderId());
            throw new SellException(ResultEnum.ORDER_FINISHED_ERROR);
        }
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());

        return orderDTO;
    }


    /**
     *  支付订单
     *  @Author 田培融  canghaihongxin@163.com
     *  @Company 不动峰 www.budongfeng.com
     *  @Date 2017/11/14/014 16:13
     */
    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        //查询订单
        OrderMaster orderMaster = orderMasterRepository.findOne(orderDTO.getOrderId());
        if(orderMaster==null){
            log.error("【订单支付】 订单异常 orderid={}",orderMaster.getOrderId());
            throw  new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        //判断订单状态
        if(!OrderStatusEnum.NEW.getCode().equals(orderMaster.getOrderStatus())){
            log.error("【订单支付】 订单状态异常  orderid={}, orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw  new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //判断支付状态
        if (!PayStatusEnum.WAIT.getCode().equals(orderMaster.getPayStatus())){
            log.error("【订单支付】 订单支付状态异常  orderid={}, payStatus={}",orderDTO.getOrderId(),orderDTO.getPayStatus());
            throw  new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改支付状态
        orderMaster.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster master = orderMasterRepository.save(orderMaster);
        if(master==null){
            log.error("【订单支付】 修改订单支付异常  orderid={}, payStatus={}",orderDTO.getOrderId(),orderDTO.getPayStatus());
            throw  new SellException(ResultEnum.ORDER_PAY_ERROR);
        }
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());

        //推荐模板消息
        pushMessageService.orderStatus(orderDTO);
        return orderDTO;
    }

    /**
     * 后台查询所有商品列表
     * @param pageable
     * @return
     */
    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        Page<OrderMaster> all = orderMasterRepository.findAll(pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTO.convert(all.getContent());
        return new PageImpl<OrderDTO>(orderDTOList,pageable,all.getTotalElements());
    }
}
