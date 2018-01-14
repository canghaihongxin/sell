package com.budongfeng.sell.utils;

import java.util.Random;

public class KeyUtil {

    /**
     *  生成唯一的主键
     *  @Author 田培融  canghaihongxin@163.com
     *  @Company 不动峰 www.budongfeng.com
     *  @Date 2017/11/13/013 19:35
     */
    public static synchronized String genUniqueKey(){
        Random random=new Random();
        Integer number=random.nextInt(9000000)+1000000;
        return System.currentTimeMillis()+String.valueOf(number);
    }
}
