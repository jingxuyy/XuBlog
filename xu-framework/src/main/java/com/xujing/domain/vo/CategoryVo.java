package com.xujing.domain.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 86136
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryVo {

    @ExcelProperty("分类id")
    private Long id;

    @ExcelProperty("分类名称")
    private String name;

    @ExcelProperty("描述")
    private String description;

    @ExcelProperty("状态0:正常,1禁用")
    private String status;
}
