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
    private int productId;
    private int cid;
    private  float price;
    private  float promotePrice;
    private String shopName;
    private Integer stock;
    private int saleNum;
    private int collectNum;
    private String  intro;
    private List<String> imgUrl;


   public ProductVO(Product product,List<String> imgUrl){
        this.productId=product.getId();
        this.cid=product.getCid();
        this.imgUrl=imgUrl;
        this.intro=product.getName();
        this.saleNum=product.getSaleNum();
        this.collectNum=product.getCollectNum();
        this.price=product.getOriginalPrice();
        this.promotePrice=product.getPromotePrice();
        this.shopName=product.getStore();
        this.stock=product.getStock();
    }
}
