package com.xujing.domain.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.xujing.domain.entity.Role;
import com.xujing.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 86136
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUpdateUserVo {

    @ExcelProperty("角色列表")
    private List<Role> roles;

    @ExcelProperty("角色id列表")
    private List<String> roleIds;

    @ExcelProperty("用户")
    private User user;
}
