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
public class GetMenuDto {

    @ExcelProperty("菜单名称")
    private String menuName;

    @ExcelProperty("菜单状态（0正常 1停用）")
    private String status;
}
