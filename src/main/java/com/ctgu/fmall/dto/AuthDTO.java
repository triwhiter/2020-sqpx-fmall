package com.ctgu.fmall.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @Auther: yanghao
 * @Date: 2020/8/1 15:11
 * @PackageName:com.ctgu.fmall.dto
 * @Description: TODO
 * @Version:V1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthDTO {
    @NotNull(message ="用户名不能为空")
    private String username;
    @NotNull(message ="密码不能为空")
    private String password;
}
