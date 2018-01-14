package com.budongfeng.sell.service.impl;

import com.budongfeng.sell.config.WechatAccountConfig;
import com.budongfeng.sell.dto.OrderDTO;
import com.budongfeng.sell.service.PushMessageService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class PushMessageServiceImpl implements PushMessageService {

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WechatAccountConfig accountConfig;

    @Override
    public void orderStatus(OrderDTO orderDTO) {
        WxMpTemplateMessage templateMessage=new WxMpTemplateMessage();
        templateMessage.setTemplateId(accountConfig.getTemplateId().get("orderStatus"));  //模板ID
        templateMessage.setToUser(orderDTO.getBuyerOpenid());  //openId
        /*
        *
        * {{first.DATA}}
            订单编号：{{keyword1.DATA}}
            订单金额：{{keyword2.DATA}}
            付款方式：{{keyword3.DATA}}
            配送方式：{{keyword4.DATA}}
            订单状态：{{keyword5.DATA}}
            {{remark.DATA}}
        * */
        List<WxMpTemplateData> data= Arrays.asList(
                new WxMpTemplateData("first","亲，记得收货哦"),
                new WxMpTemplateData("keyword1",orderDTO.getOrderId()),
                new WxMpTemplateData("keyword2","￥"+orderDTO.getOrderAmount()),
                new WxMpTemplateData("keyword3","微信"),
                new WxMpTemplateData("keyword4","顺风"),
                new WxMpTemplateData("keyword5",orderDTO.getOrderStatusEnum().getMsg()),
                new WxMpTemplateData("remark","欢迎再次光临")
        );
        templateMessage.setData(data);  //模板的信息
        try {
            log.info("微信发送消息");
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        }catch (WxErrorException e){
            log.error("【微信模板消息】 发送失败，{ }",e);
        }

    }
}
