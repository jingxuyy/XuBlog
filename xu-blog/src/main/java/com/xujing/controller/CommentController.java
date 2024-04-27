package com.xujing.controller;

import com.xujing.annotation.SystemLog;
import com.xujing.constants.SystemConstants;
import com.xujing.domain.ResponseResult;
import com.xujing.domain.dto.AddCommentDto;
import com.xujing.domain.entity.Comment;
import com.xujing.service.CommentService;
import com.xujing.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 86136
 */
@RestController
@RequestMapping("/comment")
@Api(tags = "评论", description = "评论相关接口")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/commentList")
    @SystemLog(businessName = "查看文章和用户评论")
    @ApiOperation(value = "查询文章或者用户的评论", notes = "根据SystemConstants.ARTICLE_COMMENT常量查询文章的评论，及其评论对应的子评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", paramType = "Long", value = "文章的id"),
            @ApiImplicitParam(name = "pageNum", paramType = "Integer", value = "当前查询的页号"),
            @ApiImplicitParam(name = "pageSize", paramType = "Integer", value = "当前页显示的数量"),
    })
    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize){
        return commentService.commentList(SystemConstants.ARTICLE_COMMENT, articleId, pageNum, pageSize);
    }

    @PostMapping
    @SystemLog(businessName = "添加评论")
    @ApiOperation(value = "添加评论", notes = "添加评论，并且根据Comment属性判断当前评论的目标对象(文章，友链，别人的评论)")
    @ApiImplicitParam(name = "comment", paramType = "AddCommentDto", value = "用户提交的评论addCommentDto对象")
    public ResponseResult addComment(@RequestBody AddCommentDto addCommentDto){
        Comment comment = BeanCopyUtils.copyBean(addCommentDto, Comment.class);
        return commentService.addComment(comment);
    }

    @GetMapping("/linkCommentList")
    @SystemLog(businessName = "查询友联评论")
    @ApiOperation(value = "获取友联评论", notes = "获取友链评论，并进行分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", paramType = "Integer", value = "当前查询的页号"),
            @ApiImplicitParam(name = "pageSize", paramType = "Integer", value = "当前页显示的数量")
    })
    public ResponseResult linkCommentList(Integer pageNum, Integer pageSize){
        return commentService.commentList(SystemConstants.LINK_COMMENT,null, pageNum, pageSize);
    }
}
