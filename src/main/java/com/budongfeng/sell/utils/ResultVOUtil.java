package com.budongfeng.sell.utils;

import com.budongfeng.sell.vo.ResultVo;

/**
 *  返回页面VO工具类
 *  @Author 田培融  canghaihongxin@163.com
 *  @Company 不动峰 www.budongfeng.com
 *  @Date 2017/11/13/013 13:39
 */
public class ResultVOUtil {

    public static ResultVo success(Object object){

        ResultVo resultVo=new ResultVo();
        resultVo.setData(object);
        resultVo.setCode(0);
        resultVo.setMsg("成功");
        return resultVo;
    }

    public static ResultVo success(){
        ResultVo resultVo=new ResultVo();
        resultVo.setCode(0);
        resultVo.setMsg("成功");
        return resultVo;
    }

    public static ResultVo error(Integer code, String msg){
        ResultVo resultVo=new ResultVo();
        resultVo.setCode(code);
        resultVo.setMsg(msg);
        return resultVo;

    }

}
