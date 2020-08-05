package com.ctgu.fmall.service;

import com.qiniu.common.QiniuException;

import java.io.File;
import java.io.InputStream;

/**
 * @Auther: yanghao
 * @Date: 2020/8/5 13:03
 * @PackageName:com.ctgu.fmall.service
 * @Description: TODO
 * @Version:V1.0
 */
public interface QnUploadService {
    String uploadFile(File file, String fileName) throws QiniuException;
    String uploadFile(InputStream inputStream, String fileName) throws QiniuException;
    String delete(String key) throws QiniuException;
    void afterPropertiesSet() ;
    String getUploadToken();
}
