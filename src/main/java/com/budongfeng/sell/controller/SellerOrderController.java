package com.budongfeng.sell.controller;

import com.budongfeng.sell.config.CommonConfig;
import com.budongfeng.sell.dto.OrderDTO;
import com.budongfeng.sell.enums.ResultEnum;
import com.budongfeng.sell.exception.SellException;
import com.budongfeng.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ContextLoader;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

@Controller
@RequestMapping("/seller/order")
@Slf4j
public class SellerOrderController {

    @Autowired
    private CommonConfig commonConfig;

    @Autowired
    private OrderService orderService;

    @GetMapping("/list")
    public String list(@RequestParam(name = "page",defaultValue = "1") Integer page,
                       @RequestParam(name = "size",defaultValue = "10") Integer size,
                       ModelMap map){
        PageRequest pageRequest=new PageRequest(page-1,size);
        Page<OrderDTO> orderDTOPage = orderService.findList(pageRequest);
        // orderDTOPage.getTotalElements();  //获取总记录数量
       // orderDTOPage.getTotalPages();   //总页数
        map.put("orderDTOPage",orderDTOPage);
        map.put("currentPage",page);
        map.put("size",size);
       map.put("systemName", commonConfig.getContext_path());
        return "order/list";
    }

    @GetMapping("/cancel")
    public String cancel(@RequestParam(name = "orderId") String orderId,
                         ModelMap map){
        map.put("systemName",commonConfig.getContext_path());
        //1.查询订单,捕捉异常
        OrderDTO orderDTO;
        try {
            orderDTO= orderService.findOne(orderId);
            //2. 取消订单
            orderService.cancel(orderDTO);
        } catch (SellException e) {
            log.error("【卖商取消订单】 异常={}",e.getMessage());
            map.put("msg",e.getMessage());
            return "common/error";
        }
        map.put("msg", ResultEnum.ORDER_CANCEL_CUCCESS.getMessage());
        map.put("url",commonConfig.getContext_path()+"/seller/order/list");
        return "common/success";
    }

    /**
     * 卖家查询订单详情
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("/detail")
    public String detail(@RequestParam(name = "orderId") String orderId,
                         ModelMap map){
        map.put("systemName",commonConfig.getContext_path());
        //1.查询订单,捕捉异常
        OrderDTO orderDTO;
        try {
            orderDTO= orderService.findOne(orderId);
        } catch (SellException e) {
            log.error("【卖商取消订单】 异常={}",e.getMessage());
            map.put("msg",e.getMessage());
            return "common/error";
        }
        map.put("orderDTO",orderDTO);
        return "order/detail";
    }

    @GetMapping("/finish")
    public String finish(@RequestParam(name = "orderId") String orderId,
                         ModelMap map){
        map.put("systemName",commonConfig.getContext_path());
        //1.查询订单,捕捉异常
        OrderDTO orderDTO;
        try {
            orderDTO= orderService.findOne(orderId);
            //2. 完结订单
            orderService.finish(orderDTO);
        } catch (SellException e) {
            log.error("【卖商完结订单】 异常={}",e.getMessage());
            map.put("msg",e.getMessage());
            return "common/error";
        }

        map.put("msg", ResultEnum.ORDER_FINISH_CUCCESS.getMessage());
        map.put("url",commonConfig.getContext_path()+"/seller/order/list");
        return "common/success";
    }
}
