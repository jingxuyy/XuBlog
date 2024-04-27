package com.xujing.domain.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author 86136
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AdminUserInfoVo {

    @ExcelProperty("用户")
    private UserInfoVo user;

    @ExcelProperty("权限列表")
    private List<String> permissions;

    @ExcelProperty("角色列表")
    private List<String> roles;
}
