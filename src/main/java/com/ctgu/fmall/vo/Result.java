package com.ctgu.fmall.vo;


import com.ctgu.fmall.common.ResultEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@ApiModel(value = "封装返回对象", description = "")
@NoArgsConstructor
public class Result {

    @ApiModelProperty(value = "返回code,400为失败，200为成功")
    private int code=200;

    @ApiModelProperty(value = "返回提示信息")
    private String msg="请求成功";

    @ApiModelProperty(value = "返回数据")
    private Object data=null;


    public Result(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 请求失败统一用枚举类返回
     * @param resultEnum
     */
    public Result(ResultEnum resultEnum) {
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMsg();
    }

    /**
     * 自定义消息的构造函数
     * @param msg
     * @param data
     */
    public Result(String msg,Object data) {
        this.msg = msg;
        this.data = data;
    }

    public Result(String msg) {
        this.msg = msg;
    }

    /**
     * 值返回数据，消息使用默认值
     * @param data
     */
    public Result(Object data) {
        this.data = data;
    }

}
