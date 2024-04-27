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
public class CategoryDto {

    @ExcelProperty("分类名")
    private String name;

    @ExcelProperty("状态0:正常,1禁用")
    private String status;

    @ExcelProperty("描述")
    private String description;

}
