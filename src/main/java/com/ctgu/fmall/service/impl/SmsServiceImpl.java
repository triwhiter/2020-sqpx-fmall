package com.ctgu.fmall.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.ctgu.fmall.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: yanghao
 * @Date: 2020/8/2 14:30
 * @PackageName:com.ctgu.fmall.service.impl
 * @Description: TODO
 * @Version:V1.0
 */
@Service
public class SmsServiceImpl implements SmsService {
    @Value("${sms.id}")
    private String Id;

    @Value("${sms.key}")
    private String key;

    @Value("${sms.expire}")
    private int expire;

    @Value("${sms.name}")
    private String signName;

    @Value("${sms.template}")
    private String template;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean send(String phone) {
        if(stringRedisTemplate.hasKey(phone)){
            return false;
        }
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", Id, key);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("TemplateCode", template);
        request.putQueryParameter("SignName", signName);
        HashMap<String,String> map=new HashMap<>();
        String code= UUID.randomUUID().toString().substring(0,4);
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(map));
        try {
            CommonResponse response = client.getCommonResponse(request);
            if(response.getHttpResponse().isSuccess()){
                stringRedisTemplate.opsForValue().set(phone,code,expire, TimeUnit.MINUTES);
            }
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }
}
