package com.xujing.domain.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 86136
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminRoleDto {

    @ExcelProperty("角色名称")
    private String roleName;

    @ExcelProperty("角色状态（0正常 1停用）")
    private String status;
}
