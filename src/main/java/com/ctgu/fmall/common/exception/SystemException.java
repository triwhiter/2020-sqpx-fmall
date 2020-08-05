package com.ctgu.fmall.common.exception;


import com.ctgu.fmall.common.eums.ResultEnum;
import lombok.Data;
import org.springframework.web.bind.annotation.ResponseBody;

@Data
@ResponseBody
public class SystemException extends RuntimeException {

    /**
     * 我们希望定位的错误更准确，
     * 希望不同的错误可以返回不同的错误码，所以可以自定义一个Exception
     * 注意要继承自RuntimeException，底层RuntimeException继承了Exception,
     * spring框架只对抛出的异常是RuntimeException才会进行事务回滚，
     * 如果是抛出的是Exception，是不会进行事物回滚的
     */
    public SystemException(ResultEnum resultEnum) {
        this.msg=resultEnum.getMsg();
        this.code = resultEnum.getCode();
    }

    public SystemException(String msg) {
        this.msg=msg;
    }

    private Integer code=-1;
    private String msg="请求异常";

}