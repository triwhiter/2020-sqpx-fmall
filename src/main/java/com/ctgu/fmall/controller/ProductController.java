package com.ctgu.fmall.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ctgu.fmall.dto.CProductDTO;
import com.ctgu.fmall.entity.Category;
import com.ctgu.fmall.entity.Product;
import com.ctgu.fmall.entity.ProductImage;
import com.ctgu.fmall.service.CategoryService;
//import com.ctgu.fmall.service.searchService;
import com.ctgu.fmall.service.ProductImageService;
import com.ctgu.fmall.service.ProductService;
import com.ctgu.fmall.service.SearchService;
import com.ctgu.fmall.utils.ResultUtil;
import com.ctgu.fmall.vo.ProductVO;
import com.ctgu.fmall.vo.Result;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhen
 * @since 2020-08-03
 */

@RestController
@Slf4j
@RequestMapping("/products")
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    ProductImageService productImageService;

    @Autowired
    SearchService searchService;

    @GetMapping("/{pageNo}/{pageSize}/{cid}")
    public Result getProductsByPage(@PathVariable Integer pageNo, @PathVariable("pageSize") Integer pageSize, @PathVariable int cid) throws IOException {
        IPage page = new Page<Product>(pageNo,pageSize);
        QueryWrapper<Product> wrapper= new QueryWrapper<>();
        IPage oldPage=productService.page(page,wrapper);
        List<Product>productList=oldPage.getRecords();
        List<ProductVO>productVOS=new ArrayList<>();
        HashMap hashMap=new HashMap();
        if(productList.size()==0){
            hashMap.put("list",productVOS);
            hashMap.put("total",oldPage.getTotal());
            ResultUtil.success(hashMap);
        }
        for(Product p:productList){
            log.info(p.toString());
            QueryWrapper<ProductImage> imageQueryWrapper = new QueryWrapper<>();
            imageQueryWrapper.eq("pid",p.getId());
            List<ProductImage>productImages=productImageService.list(imageQueryWrapper);
            if(productImages.size()>0){
                String imgUrl=productImages.get(0).getImgUrl();
                ProductVO productVO = new ProductVO(p,imgUrl);
                productVOS.add(productVO);
            }
        }
        hashMap.put("list",productVOS);
        hashMap.put("total",oldPage.getTotal());
        return ResultUtil.success(hashMap);
    }



    @GetMapping("/search/{keyword}/{pageNo}/{pageSize}/{cid}")
    public Result search(@PathVariable String keyword,@PathVariable int pageNo, @PathVariable int pageSize, @PathVariable int cid) throws IOException {
        log.info("搜索词：{}，页码：{}，页数：{},分类ID：{}",keyword,pageNo,pageSize,cid);
        HashMap map =searchService.searchProduct(keyword,pageNo,pageSize,cid);
        log.info("搜索情况："+map);
        return ResultUtil.success(map);
    }


    @PostMapping("/allinCate")
    @ApiOperation("获取所有目录以及目录下面的所有商品")
    public Result getAllPInC(){
        List<Category> clist = categoryService.list(null);
        List<CProductDTO> list= new ArrayList<>();
        for (Category c: clist) {
            QueryWrapper<Product> wrapper = new QueryWrapper<>();
            wrapper.eq("cid",c.getId());
            List<Product> plist= productService.list(wrapper);
            List<ProductVO> productVOS=new ArrayList<>();
            for (Product p: plist ) {
                QueryWrapper<ProductImage> imageQueryWrapper = new QueryWrapper<>();
                imageQueryWrapper.eq("pid",p.getId());
                List<ProductImage> productImages= productImageService.list(imageQueryWrapper);
                if(productImages.size()>0){
                    String imgUrl=productImages.get(0).getImgUrl();
                    ProductVO productVO = new ProductVO(p,imgUrl);
                    productVOS.add(productVO);
                }
            }
            CProductDTO cProductDTO = new CProductDTO(c.getName(),productVOS);

            list.add(cProductDTO);
        }
        return ResultUtil.success(list);
    }

    @GetMapping("/top5")
    @ApiOperation("月销排名前5的商品")
    public Result listTop5(){
        QueryWrapper<Product> wrapper =new QueryWrapper<>();
        wrapper.orderByDesc("sale_num")
                .last("limit 5");
        List<Product> plist=  productService.list(wrapper);
        List<ProductVO> productVOS=new ArrayList<>();
        for (Product p: plist ) {
            QueryWrapper<ProductImage> imageQueryWrapper = new QueryWrapper<>();
            imageQueryWrapper.eq("pid",p.getId());
            List<ProductImage> productImages= productImageService.list(imageQueryWrapper);
            if(productImages.size()>0){
                String imgUrl=productImages.get(0).getImgUrl();
                ProductVO productVO = new ProductVO(p,imgUrl);
                productVOS.add(productVO);
            }
        }
        return ResultUtil.success(productVOS);
    }

    @GetMapping("/{id}")
    @ApiOperation("通过商品编号获得商品详情")
    public Result getProductByid(@PathVariable int id){
        QueryWrapper<Product> wrapper=new QueryWrapper<>();
        wrapper.eq("id",id);
        Product product = productService.getOne(wrapper);
        QueryWrapper<ProductImage> imageQueryWrapper = new QueryWrapper<>();
        imageQueryWrapper.eq("pid",product.getId());
        List<ProductImage> productImages= productImageService.list(imageQueryWrapper);
        if(productImages.size()>0) {
            String imgUrl = productImages.get(0).getImgUrl();

            ProductVO productVO = new ProductVO(product, imgUrl);
            return ResultUtil.success(productVO);
        }else {
            ProductVO productVO = new ProductVO(product, null);
            return ResultUtil.success(productVO);
        }

    }
}

