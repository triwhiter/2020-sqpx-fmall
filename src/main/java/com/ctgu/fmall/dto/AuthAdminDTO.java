package com.ctgu.fmall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @Auther: yanghao
 * @Date: 2020/8/5 09:38
 * @PackageName:com.ctgu.fmall.dto
 * @Description: TODO
 * @Version:V1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthAdminDTO {
    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Length(min = 6,max = 25,message = "密码长度为6-25")
    private String password;

}
