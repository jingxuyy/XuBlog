package com.xujing.controller;

import com.xujing.annotation.SystemLog;
import com.xujing.domain.ResponseResult;
import com.xujing.domain.dto.AddArticleDto;
import com.xujing.domain.dto.GetArticleDto;
import com.xujing.domain.vo.AdminArticleUpdateVo;
import com.xujing.service.ArticleService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author 86136
 */
@RestController
@RequestMapping("/content/article")
@Api(tags = "文章管理", description = "博客文章管理相关接口")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
//    @PreAuthorize("@ps.hasPermission('content:article:writer')")
    @SystemLog(businessName = "添加文章")
    @ApiOperation(value = "添加文章", notes = "用于后台用户写博文")
    @ApiImplicitParam(name = "addArticleDto", type = "AddArticleDto", value = "添加文章需要的DTO类")
    public ResponseResult addArticle(@RequestBody AddArticleDto addArticleDto){
        return articleService.addArticle(addArticleDto);
    }

    @GetMapping("/list")
    @SystemLog(businessName = "查询文章概述")
    @ApiOperation(value = "查询文章概述", notes = "用于后台用户查询文章概述")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", type = "Integer", value = "分页查询的当前页码"),
            @ApiImplicitParam(name = "pageSize", type = "Integer", value = "每页显示的条目数"),
            @ApiImplicitParam(name = "articleDto", type = "GetArticleDto", value = "分页查询外加模糊查询的DTO, 1.根据文章标题 2.根据文章摘要")
    })
    public ResponseResult getArticleList(Integer pageNum, Integer pageSize, GetArticleDto articleDto){
        // 查询文章列表
        return articleService.getArticleList(pageNum, pageSize, articleDto);
    }

    @GetMapping("/{id}")
    @SystemLog(businessName = "查询文章具体信息")
    @ApiOperation(value = "查询文章具体信息", notes = "用于后台用户在修改文章时，回显文章具体信息使用")
    @ApiImplicitParam(name = "id", type = "Long", value = "文章的id值")
    public ResponseResult getArticleDetails(@PathVariable("id") Long id){
        // 根据id查询文章具体信息 以及 其标签信息
        return articleService.getArticleDetails(id);
    }

    @PutMapping
    @SystemLog(businessName = "修改文章")
    @ApiOperation(value = "修改文章", notes = "用于后台用户修改文章")
    @ApiImplicitParam(name = "article", type = "AdminArticleUpdateVo", value = "用于修改文章的VO,由于和回显的VO相同，因此使用VO代替DTO")
    public ResponseResult updateArticle(@RequestBody AdminArticleUpdateVo article){
        // 修改文章
        return articleService.updateArticle(article);
    }

    @DeleteMapping("/{id}")
    @SystemLog(businessName = "删除文章")
    @ApiOperation(value = "删除文章", notes = "用于后台用户删除")
    @ApiImplicitParam(name = "id", type = "Long", value = "文章id值")
    public ResponseResult deleteArticle(@PathVariable("id") Long id){
        // 删除文章
        return articleService.deleteArticle(id);
    }
}
