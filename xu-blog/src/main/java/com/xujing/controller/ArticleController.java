package com.xujing.controller;

import com.xujing.annotation.SystemLog;
import com.xujing.domain.ResponseResult;
import com.xujing.service.ArticleService;
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
@RequestMapping("/article")
@Api(tags = "博客文章", description = "博客文章相关接口")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/hotArticleList")
    @SystemLog(businessName = "查询热门文章")
    @ApiOperation(value = "查询热门文章", notes = "根据文章的浏览量判断热门文章，进行分页查询，每一篇文章只显示标题、摘要等，不显示具体内容")
    public ResponseResult hotArticleList(){
        ResponseResult result = articleService.hotArticleList();
        return result;
    }

    @GetMapping("/articleList")
    @SystemLog(businessName = "查询所有文章")
    @ApiOperation(value = "查询所有文章", notes = "进行分页查询，每一篇文章只显示标题、摘要等，不显示具体内容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", paramType = "Integer", value = "当前查询的页号"),
            @ApiImplicitParam(name = "pageSize", paramType = "Integer", value = "当前页显示的数量"),
            @ApiImplicitParam(name = "categoryId", paramType = "Long", value = "当前查询的文章类别")
    })
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId){
        return articleService.articleList(pageNum, pageSize, categoryId);
    }

    @GetMapping("/{id}")
    @SystemLog(businessName = "根据id查询文章")
    @ApiOperation(value = "查询指定id文章", notes = "根据id查询某一篇文章，并且显示其具体内容")
    @ApiImplicitParam(name = "id", paramType = "Long", value = "要查询的文章id")
    public ResponseResult getArticleDetail(@PathVariable Long id){
        return articleService.getArticleDetail(id);
    }


    @PutMapping("/updateViewCount/{id}")
    @SystemLog(businessName = "更新文章访问量")
    @ApiOperation(value = "设置当前id对应的文章访问量加1", notes = "当浏览器访问此接口，根据id查询文章，并且将文章访问量自增1")
    @ApiImplicitParam(name = "id", paramType = "Long", value = "浏览量自增的文章id")
    public ResponseResult updateViewCount(@PathVariable("id") Long id){
        return articleService.updateViewCount(id);
    }

}
