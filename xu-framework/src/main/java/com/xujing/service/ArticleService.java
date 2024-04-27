package com.xujing.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xujing.domain.ResponseResult;
import com.xujing.domain.dto.AddArticleDto;
import com.xujing.domain.dto.GetArticleDto;
import com.xujing.domain.entity.Article;
import com.xujing.domain.vo.AdminArticleUpdateVo;

/**
 * @author 86136
 */
public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult addArticle(AddArticleDto addArticleDto);

    ResponseResult getArticleList(Integer pageNum, Integer pageSize, GetArticleDto articleDto);

    ResponseResult getArticleDetails(Long id);

    ResponseResult updateArticle(AdminArticleUpdateVo article);

    ResponseResult deleteArticle(Long id);
}
