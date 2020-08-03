package com.ctgu.fmall.vo;

import com.ctgu.fmall.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private String imgUrl;
    private Long total;

   public ProductVO(Product product,String imgUrl,Long total){
        this.imgUrl=imgUrl;
        this.intro=product.getName();
        this.saleNum=product.getSaleNum();
        this.price=product.getOriginalPrice();
        this.shopName=product.getStore();
        this.total=total;
    }
}
