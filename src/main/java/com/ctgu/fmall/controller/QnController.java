package com.ctgu.fmall.controller;

import com.ctgu.fmall.service.QnUploadService;
import com.ctgu.fmall.utils.ResultUtil;
import com.ctgu.fmall.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @Auther: yanghao
 * @Date: 2020/8/5 13:11
 * @PackageName:com.ctgu.fmall.controller
 * @Description: TODO
 * @Version:V1.0
 */
@RestController
@RequestMapping("/qiniu")
public class QnController {
    @Autowired
    QnUploadService qnUploadService;

    @GetMapping("/token")
    public Result getToken(){
        HashMap map=new HashMap();
        map.put("token",qnUploadService.getUploadToken());
        return ResultUtil.success(map);
    }
}
