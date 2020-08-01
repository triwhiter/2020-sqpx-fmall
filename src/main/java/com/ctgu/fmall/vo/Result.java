package com.ctgu.fmall.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel(value = "封装返回对象", description = "")
public class Result {

    @ApiModelProperty(value = "返回code,400为失败，200为成功")
    private int code;

    @ApiModelProperty(value = "返回提示信息")
    private String msg;

    @ApiModelProperty(value = "返回数据")
    private Object data;


    public Result(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
