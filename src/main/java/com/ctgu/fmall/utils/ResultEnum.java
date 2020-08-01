package com.ctgu.fmall.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * @author Evan
 * @date 2019/4
 */
@Getter
@AllArgsConstructor
public enum ResultEnum {
    SUCCESS("请求成功",200),
    FAIL("操作失败",400),
    UNAUTHORIZED("请登录后进行操作",401),
    NOT_FOUND("页面不存在",404),
    INTERNAL_SERVER_ERROR("服务器繁忙",500);
    public String msg;
    public int code;
}
