package com.xujing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xujing.constants.SystemConstants;
import com.xujing.domain.ResponseResult;
import com.xujing.domain.entity.Comment;
import com.xujing.domain.entity.User;
import com.xujing.domain.vo.CommentVo;
import com.xujing.domain.vo.PageVo;
import com.xujing.enums.AppHttpCodeEnum;
import com.xujing.exception.SystemException;
import com.xujing.mapper.CommentMapper;
import com.xujing.service.CommentService;
import com.xujing.service.UserService;
import com.xujing.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2023-12-07 15:31:03
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private UserService userService;

    @Override
    public ResponseResult commentList(String type, Long articleId, Integer pageNum, Integer pageSize) {
        // 查询文章的根评论
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        // 查询对应文章id条件
        queryWrapper.eq(Objects.nonNull(articleId),Comment::getArticleId, articleId);
        // 查询根评论条件
        queryWrapper.eq(Comment::getRootId, SystemConstants.ROOT_FLAG);
        // 评论类型
        queryWrapper.eq(Comment::getType, type);
        // 根据时间排序
        queryWrapper.orderByDesc(Comment::getCreateTime);
        // 分页查询
        Page<Comment> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);

        if(page.getRecords().size()==0 && pageNum>1){
            page.setCurrent(pageNum-1);
            page(page, queryWrapper);
        }

        List<Comment> records = page.getRecords();
        // 由于前端并不需要将整个评论信息都返回，并且根据前端要求还要返回评论人昵称，因此需要将Comment封装成VO
        List<CommentVo> commentVoList = toCommentVoList(records);

        // 查询所有根评论对应的子评论集合
        for (CommentVo commentVo : commentVoList) {
            List<CommentVo> children = getChildren(commentVo.getId());
            commentVo.setChildren(children);
        }


        return ResponseResult.okResult(new PageVo(commentVoList, page.getTotal()));
    }

    @Override
    public ResponseResult addComment(Comment comment) {
        if(!StringUtils.hasText(comment.getContent())){
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        save(comment);
        return ResponseResult.okResult();
    }

    /**
     * 根据评论id查询子评论
     * @param id
     * @return
     */
    private List<CommentVo> getChildren(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRootId, id);
        queryWrapper.orderByDesc(Comment::getCreateTime);
        List<Comment> list = list(queryWrapper);
        List<CommentVo> commentVos = toCommentVoList(list);
        return commentVos;
    }


    /**
     * 将评论Comment类型封装成CommentVo类型
     * @param list
     * @return
     */
    private List<CommentVo> toCommentVoList(List<Comment> list){
        List<CommentVo> commentVoList = BeanCopyUtils.copyBeanList(list, CommentVo.class);
        // 添加CommentVo缺失属性

        // 通过createBy查询当前评论人的昵称
        // 通过toCommentUserId查询被评论人的昵称
        for (CommentVo commentVo : commentVoList) {
            User user = userService.getById(commentVo.getCreateBy());
//             String nickName = userService.getById(commentVo.getCreateBy()).getNickName();
            commentVo.setUsername(user.getNickName());
            commentVo.setAvatar(user.getAvatar());

            if(commentVo.getToCommentUserId() !=SystemConstants.ROOT_FLAG){
                User userChildren = userService.getById(commentVo.getToCommentUserId());
//                 String toCommentUserName = userService.getById(commentVo.getToCommentUserId()).getNickName();
                commentVo.setToCommentUserName(userChildren.getNickName());
                commentVo.setAvatar(userChildren.getAvatar());
            }
        }
        return commentVoList;
    }
}

