package com.ctgu.fmall.service;

import com.ctgu.fmall.vo.ProductVO;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * @Auther: yanghao
 * @Date: 2020/8/3 18:45
 * @PackageName:com.ctgu.fmall.service
 * @Description: TODO
 * @Version:V1.0
 */
public interface SearchService {
    HashMap searchProduct(String keyword, int pageNo, int pageSize, int cid) throws IOException;
}
