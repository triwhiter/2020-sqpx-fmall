package com.ctgu.fmall.dto;

import com.ctgu.fmall.entity.Product;
import com.ctgu.fmall.vo.ProductVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: xuzhen
 * @Description:
 * @Date: Created in 18:10 2020/8/2
 * @Modified By:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CProductDTO {

    private String cname;

    private List<ProductVO> products;
}
