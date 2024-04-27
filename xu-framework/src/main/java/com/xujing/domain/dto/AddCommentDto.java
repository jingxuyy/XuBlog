package com.xujing.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 86136
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "用于添加评论的实体类AddCommentDto")
public class AddCommentDto {

    @ApiModelProperty(notes = "评论类型（0代表文章评论，1代表友链评论）")
    private String type;

    @ApiModelProperty(notes = "文章id")
    private Long articleId;

    @ApiModelProperty(notes = "根评论id，指的是直接评论文章的用户id")
    private Long rootId;

    @ApiModelProperty(notes = "评论内容")
    private String content;

    @ApiModelProperty(notes = "所回复的目标评论的userId, 指的是回复给某个用户的用户id")
    private Long toCommentUserId;

    @ApiModelProperty(notes = "回复目标评论id, 指的是评论的id")
    private Long toCommentId;
}
