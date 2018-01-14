package com.budongfeng.sell.constant;

/**
 *  redis常量
 *  @Author 田培融  canghaihongxin@163.com
 *  @Company 不动峰 www.budongfeng.com
 *  @Date 2017/11/28/028 20:46
 */
public class RedisConstant {

    public static String TOKEN_PREFIX="token_%s";  //储存的KEY 以是token_为开头的
    public static Integer EXPIRE=7200; //2小时  单位秒
}
