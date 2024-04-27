package com.xujing.controller;

import com.xujing.annotation.SystemLog;
import com.xujing.domain.ResponseResult;
import com.xujing.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 86136
 */
@RestController
@RequestMapping("/category")
@Api(tags = "博客分类", description = "博客分类查询相关接口")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/getCategoryList")
    @SystemLog(businessName = "文章分类查询")
    @ApiOperation(value = "查询所有的文章分类", notes = "查询已有的文章类型")
    public ResponseResult getCategoryList(){
        return categoryService.getCategoryList();
    }
}
