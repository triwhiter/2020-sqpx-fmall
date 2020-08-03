package com.ctgu.fmall.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhen
 * @since 2020-08-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Product对象", description="12")
//@Document(indexName = "product", type = "product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品编号")
    @TableId(value = "id", type = IdType.AUTO)
//    @org.springframework.data.annotation.Id//声明es主键
    private Integer id;

    @ApiModelProperty(value = "商品名称")
    // 下面配置代表 进行存储并以ik_smart方式分词,(默认开启分词)保持的类型为text,进行查询的时候按照ik_smart方式进行分词
//    @Field(store = true, analyzer = "ik_smart", searchAnalyzer = "ik_smart", type = FieldType.Text)
    private String name;

    @ApiModelProperty(value = "店名")
//    @Field(store = true, analyzer = "ik_smart", searchAnalyzer = "ik_smart", type = FieldType.Text)
    private String store;

    @ApiModelProperty(value = "原价")
    private Float originalPrice;

    @ApiModelProperty(value = "促销价")
    private Float promotePrice;

    @ApiModelProperty(value = "库存")
    private Integer stock;

    @ApiModelProperty(value = "类别编号")
    private Integer cid;

    @ApiModelProperty(value = "收藏量")
    private Integer collectNum;

    @ApiModelProperty(value = "月销量")
    private Integer saleNum;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


}
