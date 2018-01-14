package com.budongfeng.sell.handler;

import com.budongfeng.sell.config.CommonConfig;
import com.budongfeng.sell.config.ProjectUrlConfig;
import com.budongfeng.sell.exception.AuthorizeException;
import com.budongfeng.sell.exception.ResponseBankException;
import com.budongfeng.sell.exception.SellException;
import com.budongfeng.sell.utils.ResultVOUtil;
import com.budongfeng.sell.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class SellerExceptionHandler {

    @Autowired
    private CommonConfig commonConfig;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;
    @ExceptionHandler(AuthorizeException.class)
    public ModelAndView handlerAuthorizeException(){
        log.info("【异常拦截器】拦截用户登录异常");
        Map<String,Object> map=new HashMap<>();
        map.put("systemName",commonConfig.getContext_path());
        map.put("url",commonConfig.getContext_path()+"/seller/login");
        return new ModelAndView("common/error",map);
    }

    @ExceptionHandler(SellException.class)
    @ResponseBody
    public ResultVo SellException(SellException e){
        return ResultVOUtil.error(e.getCode(),e.getMessage());
    }

    @ExceptionHandler(value = ResponseBankException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void handleResponseBankException(){

    }


}
