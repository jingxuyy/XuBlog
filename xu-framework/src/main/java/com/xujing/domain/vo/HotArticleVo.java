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
public class HotArticleVo {

    @ExcelProperty("文章id")
    private Long id;

    @ExcelProperty("标题")
    private String title;

    @ExcelProperty("访问量")
    private Long viewCount;
}
