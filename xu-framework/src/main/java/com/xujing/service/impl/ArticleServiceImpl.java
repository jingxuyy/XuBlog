package com.xujing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xujing.constants.SystemConstants;
import com.xujing.domain.ResponseResult;
import com.xujing.domain.dto.AddArticleDto;
import com.xujing.domain.dto.GetArticleDto;
import com.xujing.domain.entity.Article;
import com.xujing.domain.entity.ArticleTag;
import com.xujing.domain.entity.Category;
import com.xujing.domain.vo.*;
import com.xujing.mapper.ArticleMapper;
import com.xujing.service.ArticleService;
import com.xujing.service.ArticleTagService;
import com.xujing.service.CategoryService;
import com.xujing.utils.BeanCopyUtils;
import com.xujing.utils.RedisCache;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 86136
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleTagService articleTagService;

    /**
     * 查询热门文章
     * 需求：
     *  1. 查询浏览量最高的10篇文章信息
     *  2. 不能展示草稿文章
     *  3. 不能展示已删除文章
     *  4. 查询结果按照浏览量降序排序
     *
     *  不能将数据库里的数据全部信息返回给前端，当前接口对应的前端其实只需要文章的id, title, viewCount三个数据即可
     *  像这样在数据库查询的信息进行简化 直接 在前端展示 可以创建对应的VO类，这个类成员变量就是当前需要展示的数据
     */
    @Override
    public ResponseResult hotArticleList() {
        // 定义查询条件
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        // 正式文章条件
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        // 浏览量排序
        queryWrapper.orderByDesc(Article::getViewCount);
        // 这里使用分页查询10个
        Page<Article> page = new Page<>(SystemConstants.PAGE_NUMBER, SystemConstants.PAGE_SIZE);
        page(page, queryWrapper);

         List<Article> articles = page.getRecords();

        articles = articles.stream().peek(article -> {
            Integer viewCount = redisCache.getCacheMapValue(SystemConstants.REDIS_VIEW_COUNT, article.getId().toString());
            article.setViewCount(viewCount.longValue());
        }).collect(Collectors.toList());

        // 使用自定义工具转换
        List<HotArticleVo> articleVos = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);
        return ResponseResult.okResult(articleVos);
    }


    /**
     * 在首页和分类页面都需要查询文章列表
     * 首页： 查询所有的文章
     * 分类页面：查询对应分类下的文章
     * 1. 只能查询正式发布的文章
     * 2. 置顶的文章要显示在最前面
     */
    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        // 查询条件
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        // 如果categoryId不为空，则表明查询的是分类文章，否则是首页查询所有
        queryWrapper.eq(Objects.nonNull(categoryId) && categoryId>=SystemConstants.LEGAL_ID,Article::getCategoryId, categoryId);
        // 状态必须是正式发布的
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        // 对isTop进行排序
        queryWrapper.orderByDesc(Article::getIsTop);
        // 分页查询
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);

        if(page.getRecords().size()==0 && pageNum>1){
            page.setCurrent(pageNum-1);
            page(page, queryWrapper);
        }

        // 查询分类Id
        List<Article> articles = page.getRecords();

        articles = articles.stream()
                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .peek(article -> {
                    Integer viewCount = redisCache.getCacheMapValue(SystemConstants.REDIS_VIEW_COUNT, article.getId().toString());
                    article.setViewCount(viewCount.longValue());
                })
                .collect(Collectors.toList());


        // 封装查询结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(articles, ArticleListVo.class);


        // 分页查询结果封装进PageVo
        PageVo pageVo = new PageVo(articleListVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }


    /**
     * 查询文章详情
     */
    @Override
    public ResponseResult getArticleDetail(Long id) {
        // 根据id查询文章
        Article article = getById(id);
        // 从redis获取viewCount
        Integer viewCount = redisCache.getCacheMapValue(SystemConstants.REDIS_VIEW_COUNT, id.toString());
        article.setViewCount(viewCount.longValue());
        // 转换成VO
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        // 根据分类id查询分类名
        String name = categoryService.getById(article.getCategoryId()).getName();
        if(name!=null){
            articleDetailVo.setCategoryName(name);
        }
        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        // 更新redis中对应 id 的浏览量
        redisCache.incrementCacheMapValue(SystemConstants.REDIS_VIEW_COUNT, id.toString(), SystemConstants.REDIS_INCRYBY);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult addArticle(AddArticleDto addArticleDto) {
        // 添加博客
        Article article = BeanCopyUtils.copyBean(addArticleDto, Article.class);
        save(article);

        List<ArticleTag> articleTags = addArticleDto.getTags().stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());

        // 添加 博客和标签的关联
        articleTagService.saveBatch(articleTags);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getArticleList(Integer pageNum, Integer pageSize, GetArticleDto articleDto) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(articleDto.getTitle()), Article::getTitle, articleDto.getTitle());
        queryWrapper.like(StringUtils.hasText(articleDto.getSummary()), Article::getSummary, articleDto.getSummary());

        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);

        if(page.getRecords().size()==0 && pageNum>1){
            page.setCurrent(pageNum-1);
            page(page, queryWrapper);
        }

        List<AdminArticleVo> adminArticleVos = BeanCopyUtils.copyBeanList(page.getRecords(), AdminArticleVo.class);
        return ResponseResult.okResult(new PageVo(adminArticleVos, page.getTotal()));
    }

    @Override
    public ResponseResult getArticleDetails(Long id) {
        Article article = getById(id);
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(ArticleTag::getTagId);
        queryWrapper.eq(ArticleTag::getArticleId, id);
        List<ArticleTag> list = articleTagService.list(queryWrapper);
        AdminArticleUpdateVo articleUpdateVo = BeanCopyUtils.copyBean(article, AdminArticleUpdateVo.class);
        List<String> collect = list.stream().map(articleTag -> articleTag.getTagId().toString()).collect(Collectors.toList());
        articleUpdateVo.setTags(collect);
        return ResponseResult.okResult(articleUpdateVo);
    }

    @Override
    public ResponseResult updateArticle(AdminArticleUpdateVo article) {
        Article article1 = BeanCopyUtils.copyBean(article, Article.class);
        updateById(article1);
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getArticleId, article.getId());
        articleTagService.remove(queryWrapper);
        List<ArticleTag> collect = article.getTags().stream()
                .map(tagId -> new ArticleTag(article.getId(), Long.valueOf(tagId)))
                .collect(Collectors.toList());
        articleTagService.saveBatch(collect);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteArticle(Long id) {
        removeById(id);
        return ResponseResult.okResult();
    }
}
