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
@ApiModel(description = "用于注册使用的实体类UserLoginDto")
public class UserLoginDto {

    @ApiModelProperty(notes = "用于登录使用的用户名")
    private String userName;

    @ApiModelProperty(notes = "用于登录使用的密码")
    private String password;
}
