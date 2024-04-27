package com.xujing.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xujing.domain.ResponseResult;
import com.xujing.domain.entity.Comment;


/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2023-12-07 15:31:02
 */
public interface CommentService extends IService<Comment> {

    ResponseResult commentList(String type, Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(Comment comment);
}

