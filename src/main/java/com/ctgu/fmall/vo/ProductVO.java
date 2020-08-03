package com.ctgu.fmall.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: yanghao
 * @Date: 2020/8/3 09:22
 * @PackageName:com.ctgu.fmall.vo
 * @Description: 商品列表展示
 * @Version:V1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductVO {
    private  float price;
    private String shopName;
    private int saleNum;
    private String  intro;
}
