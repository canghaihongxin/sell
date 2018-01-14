package com.budongfeng.sell.aspect;

import com.budongfeng.sell.constant.CookieConstant;
import com.budongfeng.sell.constant.RedisConstant;
import com.budongfeng.sell.exception.AuthorizeException;
import com.budongfeng.sell.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {

    @Autowired
    private StringRedisTemplate redisTemplate;
    
    @Pointcut("execution(public * com.budongfeng.sell.controller.Seller*.*(..))"+
            "&& !execution(public * com.budongfeng.sell.controller.SellerUserController.*(..))")
    public void verify(){
    }

    @Before("verify()")
    public void doVerify(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();//注意包名

        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if(cookie==null){
            log.warn("【AOP控制用户访问】COOKIE中找不到token");
            throw new AuthorizeException();
        }

        String username = redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
        if(StringUtils.isEmpty(username)){
            log.warn("【AOP控制用户访问】redis中找不到token");
            throw new AuthorizeException();
        }

    }
}
