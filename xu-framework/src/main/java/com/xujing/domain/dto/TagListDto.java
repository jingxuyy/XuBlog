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
public class TagListDto {

    @ExcelProperty("标签名称")
    private String name;

    @ExcelProperty("标签描述")
    private String remark;
}
