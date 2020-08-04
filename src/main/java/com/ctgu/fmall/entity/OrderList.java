package com.ctgu.fmall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.ctgu.fmall.dto.OrderDTO;
import com.ctgu.fmall.utils.CommonUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhen
 * @since 2020-08-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="OrderList对象", description="")
@AllArgsConstructor
@NoArgsConstructor
public class OrderList implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户留言")
    private String userMessage;

    @ApiModelProperty(value = "支付金额")
    private Float amount;

    @ApiModelProperty(value = "会员编号")
    private Integer uid;

    @ApiModelProperty(value = "地址编号")
    private Integer aid;

    @ApiModelProperty(value = "订单状态")
    private String status;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    public OrderList(OrderDTO orderDTO){
        User user= CommonUtil.getCurrentUser();
        this.aid=orderDTO.getAid();
        this.amount=orderDTO.getAmount();
        this.uid=user.getId();
        this.userMessage=orderDTO.getMessage();
    }

}
