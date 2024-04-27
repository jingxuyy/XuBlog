package com.xujing.domain.vo;

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
public class TagVo {

    @ExcelProperty("标签id")
    private Long id;

    @ExcelProperty("标签名")
    private String name;

    @ExcelProperty("备注")
    private String remark;
}
