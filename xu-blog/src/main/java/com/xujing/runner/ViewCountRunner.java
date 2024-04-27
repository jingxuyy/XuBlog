package com.xujing.runner;

import com.xujing.annotation.SystemLog;
import com.xujing.constants.SystemConstants;
import com.xujing.domain.entity.Article;
import com.xujing.mapper.ArticleMapper;
import com.xujing.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 86136
 */
@Component
public class ViewCountRunner implements CommandLineRunner {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RedisCache redisCache;

    @Override
    public void run(String... args) throws Exception {
        // 查询博客信息  id  viewCount
        List<Article> articles = articleMapper.selectList(null);
        Map<String, Integer> viewCountMap = articles.stream().collect(Collectors.toMap(
                article->article.getId().toString(), article -> article.getViewCount().intValue()));
        redisCache.setCacheMap(SystemConstants.REDIS_VIEW_COUNT, viewCountMap);

    }
}
