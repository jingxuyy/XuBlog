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
public class AdminArticleVo {

    @ExcelProperty("文章id")
    private Long id;

    @ExcelProperty("标题")
    private String title;

    @ExcelProperty("文章内容")
    private String content;

    @ExcelProperty("文章摘要")
    private String summary;

    @ExcelProperty("所属分类的分类id")
    private Long categoryId;

    @ExcelProperty("缩略图")
    private String thumbnail;

    @ExcelProperty("是否置顶 (0否，1是)")
    private String isTop;

    @ExcelProperty("状态 (0已发布，1草稿)")
    private String status;

    @ExcelProperty("访问量")
    private Long viewCount;

    @ExcelProperty("是否允许评论 1是，0否")
    private String isComment;

    @ExcelProperty("创建时间")
    private Date createTime;

    @ExcelProperty("删除标志 (0代表未删除，1代表已删除)")
    private Integer delFlag;
}
