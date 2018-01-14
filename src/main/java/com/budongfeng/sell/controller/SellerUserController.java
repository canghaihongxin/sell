package com.budongfeng.sell.controller;

import com.budongfeng.sell.config.CommonConfig;
import com.budongfeng.sell.config.ProjectUrlConfig;
import com.budongfeng.sell.constant.CookieConstant;
import com.budongfeng.sell.constant.RedisConstant;
import com.budongfeng.sell.dataobject.SellerInfo;
import com.budongfeng.sell.enums.ResultEnum;
import com.budongfeng.sell.exception.SellException;
import com.budongfeng.sell.service.SellerInfoService;
import com.budongfeng.sell.utils.CookieUtil;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/seller")
public class SellerUserController {

    @Autowired
    private CommonConfig commonConfig;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;
    @Autowired
    private SellerInfoService sellerInfoService;

    @Autowired
    private StringRedisTemplate redisTemplate;


    @RequestMapping("/login")
    public String loginPage(ModelMap map){
        map.put("systemName",commonConfig.getContext_path());
        return "seller/login";
    }

    @PostMapping("/doLogin")
    public ModelAndView login(@RequestParam(name = "username") String username,
                              @RequestParam(name = "password") String password,
                              HttpServletResponse response,
                              ModelMap map){
        map.put("systemName",commonConfig.getContext_path());

        //2.校验登录信息
        try {
            SellerInfo userInfo = sellerInfoService.findByUsername(username);
            if(!userInfo.getPassword().equals(password))
            throw new SellException(ResultEnum.SELL_LOGIN_ERROR);
        } catch (SellException e) {
            map.put("msg",e.getMessage());
            map.put("url",commonConfig.getContext_path()+"/seller/login");
            return  new ModelAndView("common/error");
        }
        //2.设置token至redis
        String token= UUID.randomUUID().toString();
        Integer expire = RedisConstant.EXPIRE;
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX,token),username,expire, TimeUnit.SECONDS);
        //3.设置token至cookie
        CookieUtil.set(response, CookieConstant.TOKEN,token,expire);
         return  new ModelAndView("redirect:"+projectUrlConfig.getSell()+"/sell/seller/order/list");
    }

    /**
     * 退出
     * @param request
     * @param map
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response,
                         ModelMap map){

        //1.获取cookie
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if(cookie!=null){
            //2.删除redis
            redisTemplate.opsForValue().getOperations().delete(cookie.getValue());
        }
        //3. 删除cookie
        CookieUtil.set(response,CookieConstant.TOKEN,null,0);
        //4.跳转页面
        map.put("systemName",commonConfig.getContext_path());
        map.put("msg",ResultEnum.SELLER_LOGOUT_SUCCESS);
        map.put("url",commonConfig.getContext_path()+"/seller/login");
        return "/common/success";
    }

}
