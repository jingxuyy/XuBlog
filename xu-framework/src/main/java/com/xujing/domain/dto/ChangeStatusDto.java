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
public class ChangeStatusDto {

    @ExcelProperty("角色id")
    private String roleId;

    @ExcelProperty("角色状态")
    private String status;
}
