package com.budongfeng.sell.controller;

import com.budongfeng.sell.converter.OrderForm2OrderDTO;
import com.budongfeng.sell.dto.OrderDTO;
import com.budongfeng.sell.enums.ResultEnum;
import com.budongfeng.sell.exception.ResponseBankException;
import com.budongfeng.sell.exception.SellException;
import com.budongfeng.sell.form.OrderForm;
import com.budongfeng.sell.service.BuyerService;
import com.budongfeng.sell.service.OrderService;
import com.budongfeng.sell.utils.ResultVOUtil;
import com.budongfeng.sell.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  用户订单controller
 *  @Author 田培融  canghaihongxin@163.com
 *  @Company 不动峰 www.budongfeng.com
 *  @Date 2017/11/14/014 18:49
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;
    //创建订单
    @PostMapping("/create")
    public ResultVo<Map<String,String>> creatOrder(@Valid OrderForm orderForm,
                                                   BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            log.error("【创建订单】 参数不正确 roderForm={}",orderForm);
            throw  new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        OrderDTO orderDTO = OrderForm2OrderDTO.convert(orderForm);
        OrderDTO createOrderDTO = orderService.create(orderDTO);
        if(createOrderDTO==null){
            log.error("【创建订单】 创建订单失败 orderForm={}",orderForm );
                throw new SellException(ResultEnum.ORDER_CREATE_ERROR);
        }

        Map<String,String> map=new HashMap<>();
        map.put("orderId",createOrderDTO.getOrderId());
        return ResultVOUtil.success(map);
    }
    //订单列表
    @GetMapping("/list")
    public ResultVo<List<OrderDTO>> list(@RequestParam(value = "openid") String openid,
                                         @RequestParam(value = "page",defaultValue = "0") Integer page,
                                         @RequestParam(value = "size",defaultValue = "10") Integer size){

        //校验
        if(StringUtils.isEmpty(openid)){
            log.error("【查询订单列表】 openid不能为空");
            throw new  SellException(ResultEnum.PARAM_ERROR);
        }

        PageRequest pageRequest=new PageRequest(page,size);
        Page<OrderDTO> list = orderService.findList(openid, pageRequest);

        return ResultVOUtil.success(list);
    }

    //查询订单详情
    @GetMapping("/detail")
    public ResultVo<OrderDTO> detail(@RequestParam(value = "openid") String openid,
                                           @RequestParam(value = "orderId") String orderId){
        OrderDTO result = buyerService.findOrderOne(openid,orderId);
        return ResultVOUtil.success(result);
    }

    //取消订单
    @PostMapping("/cancel")
    public ResultVo cancel(@RequestParam(value = "openid") String openid,
                           @RequestParam(value = "orderId") String orderId){
      /*  OrderDTO orderDTO = orderService.findOne(orderId);
         orderService.cancel(orderDTO);*/
         buyerService.cancelOrder(openid,orderId);
        return ResultVOUtil.success();
    }




}
