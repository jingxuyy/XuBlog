package com.xujing.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xujing.domain.ResponseResult;
import com.xujing.domain.dto.CategoryDto;
import com.xujing.domain.entity.Category;
import com.xujing.domain.vo.CategoryVo;

import javax.servlet.http.HttpServletResponse;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2023-12-02 16:13:46
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();

    ResponseResult listAllCategory();

    ResponseResult getList(Integer pageNum, Integer pageSize, CategoryDto categoryDto);

    void downFile(String s, HttpServletResponse response);

    ResponseResult addCategory(CategoryDto categoryDto);

    ResponseResult getCategoryById(Long id);

    ResponseResult updateCategory(CategoryVo categoryVo);

    ResponseResult deleteCategory(Long id);
}

