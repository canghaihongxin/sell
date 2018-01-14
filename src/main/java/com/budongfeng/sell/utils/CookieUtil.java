package com.budongfeng.sell.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 *  Cookie 工具类
 *  @Author 田培融  canghaihongxin@163.com
 *  @Company 不动峰 www.budongfeng.com
 *  @Date 2017/11/28/028 21:02
 */
public class CookieUtil {

    /**
     * 设置cookie
     * @param response
     * @param name
     * @param value
     * @param maxAge
     */
    public static  void set(HttpServletResponse response,
                            String name,
                            String value,
                            int maxAge){

        Cookie cookie=new Cookie(name,value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    /**
     * 获取cookie
     * @param request
     * @param name
     * @return
     */
    public static Cookie get(HttpServletRequest request, String name){
        Map<String, Cookie> cookieMap = readCookie(request);
        if(cookieMap.containsKey(name)){
            return cookieMap.get(name);
        }
        return null;
    }

    /**
     *  获取request中的cookiemap
     * @param request
     * @return
     */
    public static Map<String,Cookie> readCookie(HttpServletRequest request){
        Map<String,Cookie> cookieMap= new HashMap<>();
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(),cookie);
            }
        }
        return cookieMap;
    }


}
