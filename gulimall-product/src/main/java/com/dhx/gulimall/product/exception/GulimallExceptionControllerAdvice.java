package com.dhx.gulimall.product.exception;

import com.dhx.gulimall.common.exception.BizCode;
import com.dhx.gulimall.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

/**
 * @author dhx_
 * @className GulimallExceptionControllerAdvice
 * @date : 2023/01/13/ 15:43
 *  集中处理所有异常
 **/
@Slf4j
@RestControllerAdvice(basePackages = "com.dhx.gulimall.product.controller") // 用于统一处理异常
@Component
public class GulimallExceptionControllerAdvice {


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handlerValidException(MethodArgumentNotValidException e){
        log.error("数据校验出现问题 {} , 异常类型 :{}",e.getMessage(),e.getClass());
        BindingResult result = e.getBindingResult();
        HashMap<String, String> errorMap = new HashMap<>();
        result.getFieldErrors().forEach(fieldError->{
            String message = fieldError.getDefaultMessage();
            String field = fieldError.getField();
            errorMap.put(field,message);
        });
        return R.error(BizCode.VALID_EXCEPTION.getCode(),BizCode.VALID_EXCEPTION.getMessage()).put("data",errorMap);
    }
    @ExceptionHandler(value = Throwable.class)
    public R handlerException(Throwable e){
        log.error("数据校验出现问题 {} , 异常类型 :{}",e.getMessage(),e.getClass());
        return R.error(BizCode.UNKNOW_EXCEPTION);
    }



}
