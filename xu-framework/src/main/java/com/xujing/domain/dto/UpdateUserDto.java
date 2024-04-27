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
@ApiModel(description = "用于更新用户信息的实体类UpdateUserDto")
public class UpdateUserDto {

    @ApiModelProperty(notes = "指定更新用户的id")
    private Long id;

    @ApiModelProperty(notes = "用于更新用户的昵称")
    private String nickName;

    @ApiModelProperty(notes = "用于更新用户的邮箱")
    private String email;

    @ApiModelProperty(notes = "用于更新用户性别（0男，1女，2未知）")
    private String sex;

    @ApiModelProperty(notes = "用于更新用户的头像")
    private String avatar;
}
