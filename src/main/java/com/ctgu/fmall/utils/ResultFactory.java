package com.ctgu.fmall.utils;

import com.ctgu.fmall.vo.Result;

/**
 * @author Evan
 * @date 2019/4
 */
public class ResultFactory {


    /**
     * 请求成功，返回自定义消息和数据
     * @param data
     * @param message
     * @return
     */
    public static Result buildSuccessResult(Object data,String message) {
        Result result = new Result(message,data);
        return result;
    }

    /**
     * 请求成功，返回数据，消息和状态码使用默认值
     * @param data
     * @return
     */
    public static Result buildSuccessResult(Object data) {
        Result result = new Result(data);
        return result;
    }

    /**
     * 返回失败的提示信息
     * @param resultEnum
     * @return
     */
    public static Result buildFailResult(ResultEnum resultEnum) {
        Result result = new Result(resultEnum);
        return result;
    }

    
}
