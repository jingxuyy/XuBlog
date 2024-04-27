package com.xujing.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xujing.domain.entity.ArticleTag;
import com.xujing.mapper.ArticleTagMapper;
import com.xujing.service.ArticleTagService;
import org.springframework.stereotype.Service;

/**
 * 文章标签关联表(ArticleTag)表服务实现类
 *
 * @author makejava
 * @since 2023-12-11 19:41:08
 */
@Service("articleTagService")
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

}

