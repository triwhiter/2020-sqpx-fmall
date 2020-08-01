package com.ctgu.fmall.common.exception;


import com.ctgu.fmall.utils.ResultUtil;
import com.ctgu.fmall.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value =Exception.class)
    @ResponseBody
    public Result commonExceptionHandle(Exception e) {
        log.error(e.toString());  // 记录错误信息
        if (e instanceof SystemException) {
            SystemException systemException = (SystemException) e;
            return ResultUtil.error(systemException.getMsg());
        } else {
            return ResultUtil.error(e.getMessage());
        }
    }


    //DTO参数校验的异常处理
    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result argumentExceptionHandle(MethodArgumentNotValidException e){
        log.error(e.toString());  // 记录错误信息
        List<ObjectError> errorList=e.getBindingResult().getAllErrors();
        String error="参数错误";
        for (ObjectError objectError : errorList) {
            error=objectError.getDefaultMessage();
        }
        return ResultUtil.error(error);
    }


}