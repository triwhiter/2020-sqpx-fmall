package com.ctgu.fmall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @Auther: yanghao
 * @Date: 2020/8/4 15:43
 * @PackageName:com.ctgu.fmall.dto
 * @Description: TODO
 * @Version:V1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    @Length(max = 100,message = "留言内容不能多于100字")
    private String message;
    @NotBlank(message = "收货地址不能为空")
    private Integer aid;
    @NotBlank(message = "商品ID不能为空")
    private List<Integer> pids;
    @NotBlank(message = "商品数量不能为空")
    private List<Integer> nums;
    @NotBlank(message = "商品总价不能为空")
    private float amount;
}
