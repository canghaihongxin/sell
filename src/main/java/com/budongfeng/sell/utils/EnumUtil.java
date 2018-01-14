package com.budongfeng.sell.utils;

import com.budongfeng.sell.enums.CodeEnum;

public class EnumUtil {

    /**
     * 获取枚举
     * @param code
     * @param enumClass
     * @param <T>
     * @return
     */
    public static  <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass){

        for (T each:enumClass.getEnumConstants()){
            if(code.equals(each.getCode())){
                return  each;
            }
        }
        return null;
    }
}
