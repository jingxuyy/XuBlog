package com.xujing.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 86136
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "用于用户注册的实体类RegisterUserDto")
public class RegisterUserDto {

    @ApiModelProperty(notes = "注册使用的用户名")
    private String userName;

    @ApiModelProperty(notes = "注册使用的昵称")
    private String nickName;

    @ApiModelProperty(notes = "注册使用的密码")
    private String password;

    @ApiModelProperty(notes = "注册使用的邮箱")
    private String email;
}
