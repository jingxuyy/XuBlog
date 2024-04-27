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
public class GetArticleDto {

    @ExcelProperty("标题")
    private String title;

    @ExcelProperty("文章摘要")
    private String summary;
}
