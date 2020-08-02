package com.ctgu.fmall.service;

/**
 * @Auther: yanghao
 * @Date: 2020/8/2 14:29
 * @PackageName:com.ctgu.fmall.service
 * @Description: 短信发送接口
 * @Version:V1.0
 */
public interface SmsService {
    boolean send(String phone);
}
