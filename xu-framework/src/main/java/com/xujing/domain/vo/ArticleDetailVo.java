package com.xujing.domain.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 86136
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDetailVo {

    @ExcelProperty("文章id")
    private Long id;

    @ExcelProperty("标题")
    private String title;

    @ExcelProperty("文章摘要")
    private String summary;

    @ExcelProperty("文章内容")
    private String content;

    @ExcelProperty("所属分类的分类id")
    private Long categoryId;

    @ExcelProperty("所属分类名")
    private String categoryName;

    @ExcelProperty("缩略图")
    private String thumbnail;

    @ExcelProperty("访问量")
    private Long viewCount;

    @ExcelProperty("创建时间")
    private Date createTime;
}
