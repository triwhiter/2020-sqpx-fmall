package com.ctgu.fmall.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ctgu.fmall.dto.CProductDTO;
import com.ctgu.fmall.entity.Category;
import com.ctgu.fmall.entity.Comment;
import com.ctgu.fmall.entity.Product;
import com.ctgu.fmall.entity.ProductImage;
import com.ctgu.fmall.service.CategoryService;
import com.ctgu.fmall.service.ESProductService;
import com.ctgu.fmall.service.ProductImageService;
import com.ctgu.fmall.service.ProductService;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Resource(name = "highLevelClient")
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    ESProductService esProductService;

    @GetMapping("/{pageNo}/{pageSize}")
    public Result getProductsByPage(@PathVariable Integer pageNo, @PathVariable("pageSize") Integer pageSize) throws IOException {
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
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("10s");
        for(Product p:productList){
            log.info(p.toString());
            QueryWrapper<ProductImage> imageQueryWrapper = new QueryWrapper<>();
            imageQueryWrapper.eq("pid",p.getId());
            List<ProductImage>productImages=productImageService.list(imageQueryWrapper);
            if(productImages.size()>0){
                String imgUrl=productImages.get(0).getImgUrl();
                ProductVO productVO = new ProductVO(p,imgUrl);
                productVOS.add(productVO);
                bulkRequest.add(new IndexRequest("product").source(JSON.toJSONString(p), XContentType.JSON));
            }
        }
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        log.warn("索引是否建立成功："+!bulk.hasFailures()+"");
        hashMap.put("list",productVOS);
        hashMap.put("total",oldPage.getTotal());
        return ResultUtil.success(hashMap);
    }

    @GetMapping("/search")
    public Result search(@RequestParam String keyword) throws IOException {
        log.info("搜索词："+keyword);
        return ResultUtil.success(esProductService.searchProduct(keyword));
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

    @GetMapping("/{pid}")
    @ApiOperation("通过商品编号获得商品详情")
    public Result getProductByid(@PathVariable int pid){
        QueryWrapper<Product> wrapper=new QueryWrapper<>();
        wrapper.eq("pid",pid);
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

