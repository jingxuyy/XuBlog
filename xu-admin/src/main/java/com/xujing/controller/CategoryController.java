package com.xujing.controller;

import com.xujing.annotation.SystemLog;
import com.xujing.domain.ResponseResult;
import com.xujing.domain.dto.CategoryDto;
import com.xujing.domain.vo.CategoryVo;
import com.xujing.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * @author 86136
 */
@RestController
@RequestMapping("/content/category")
@Api(tags = "分类管理", description = "分类管理相关接口")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/listAllCategory")
    @SystemLog(businessName = "查询所有分类")
    @ApiOperation(value = "查询所有分类", notes = "用于后台用户查询所有分类，并且展示全部，不进行分页展示")
    public ResponseResult listAllCategory(){
        return categoryService.listAllCategory();
    }

    @GetMapping("/list")
    @SystemLog(businessName = "分页查询所有分类")
    @ApiOperation(value = "分页查询所有分类", notes = "用于后台用户分页查询所有分类，默认每页显示10条数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", type = "Integer", value = "分页查询的当前页码"),
            @ApiImplicitParam(name = "pageSize", type = "Integer", value = "每页显示的条目数"),
            @ApiImplicitParam(name = "categoryDto", type = "CategoryDto", value = "分页查询外加模糊查询的DTO, 1.根据分类名称 2.根据分类的状态")
    })
    public ResponseResult list(@RequestParam("pageNum") Integer pageNum,
                               @RequestParam("pageSize") Integer pageSize,
                               CategoryDto categoryDto){
        return categoryService.getList(pageNum, pageSize, categoryDto);
    }

    @PreAuthorize("@ps.hasPermission('content:category:export')")
    @GetMapping("/export")
    @SystemLog(businessName = "分类数据导出")
    @ApiOperation(value = "分类数据导出", notes = "用于后台用户导出所有分类数据，保存到用户电脑，文件名为 分类.xlsx")
    @ApiImplicitParam(name = "response", type = "HttpServletResponse", value = "响应体，SpringMVC自动赋值")
    public void export(HttpServletResponse response){
        categoryService.downFile("分类.xlsx", response);
    }

    @PostMapping
    @SystemLog(businessName = "添加分类")
    @ApiOperation(value = "添加分类", notes = "用于后台用户添加分类")
    @ApiImplicitParam(name = "categoryDto", type = "CategoryDto", value = "添加分类的DTO")
    public ResponseResult addCategory(@RequestBody CategoryDto categoryDto){
        // 新增分类
        return categoryService.addCategory(categoryDto);
    }

    @GetMapping("/{id}")
    @SystemLog(businessName = "根据id查询分类")
    @ApiOperation(value = "根据id查询分类", notes = "根据id查询分类，用于在修改分类信息时，回显数据")
    @ApiImplicitParam(name = "id", type = "Long", value = "分类id值")
    public ResponseResult getCategoryById(@PathVariable("id") Long id){
        // 根据id查询分类，用于回显数据
        return categoryService.getCategoryById(id);
    }

    @PutMapping
    @SystemLog(businessName = "修改分类")
    @ApiOperation(value = "修改分类", notes = "用于后台用户修改分类信息")
    @ApiImplicitParam(name = "categoryVo", type = "CategoryVo", value = "修改分类信息的VO, 由于和查询分类信息的VO一样，因此使用VO代替DTO")
    public ResponseResult updateCategory(@RequestBody CategoryVo categoryVo){
        // 更新分类
        return categoryService.updateCategory(categoryVo);
    }

    @DeleteMapping("/{id}")
    @SystemLog(businessName = "删除分类")
    @ApiOperation(value = "删除分类", notes = "用于后台用户删除分类信息")
    @ApiImplicitParam(name = "id", type = "Long", value = "分类id值")
    public ResponseResult deleteCategory(@PathVariable("id") Long id){
        // 删除分类
        return categoryService.deleteCategory(id);
    }
}
