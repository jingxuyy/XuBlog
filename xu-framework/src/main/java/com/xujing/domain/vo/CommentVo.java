package com.xujing.domain.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author 86136
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentVo {

    @ExcelProperty("评论id")
    private Long id;

    @ExcelProperty("文章id")
    private Long articleId;

    @ExcelProperty("根评论id")
    private Long rootId;

    @ExcelProperty("评论内容")
    private String content;

    @ExcelProperty("所回复的目标评论的userId")
    private Long toCommentUserId;

    @ExcelProperty("所回复的目标评论的userName")
    private String toCommentUserName;

    @ExcelProperty("回复目标评论id")
    private Long toCommentId;

    @ExcelProperty("创建人id")
    private Long createBy;

    @ExcelProperty("创建时间")
    private Date createTime;

    @ExcelProperty("评论人名称")
    private String username;

    @ExcelProperty("子评论")
    private List<CommentVo> children;

    @ExcelProperty("头像")
    private String avatar;
}
