package com.xujing.domain.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 86136
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageVo {

    @ExcelProperty("分页数据")
    private List rows;

    @ExcelProperty("分页总条目数")
    private Long total;
}
