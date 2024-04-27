package com.xujing.controller;

import com.xujing.annotation.SystemLog;
import com.xujing.domain.ResponseResult;
import com.xujing.domain.dto.TagListDto;
import com.xujing.domain.vo.TagVo;
import com.xujing.service.TagService;
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
@RequestMapping("/content/tag")
@Api(tags = "标签管理", description = "标签管理相关接口")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/start")
    @SystemLog(businessName = "启动测试接口")
    @ApiOperation(value = "启动测试接口", notes = "用于后台启动测试接口，不用于生产环境")
    public ResponseResult start(){
        return ResponseResult.okResult(tagService.list());
    }

    @GetMapping("/list")
    @SystemLog(businessName = "标签分页查询")
    @ApiOperation(value = "标签分页查询", notes = "用于后台用户标签分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", type = "Integer", value = "分页查询的当前页码"),
            @ApiImplicitParam(name = "pageSize", type = "Integer", value = "每页显示的条目数"),
            @ApiImplicitParam(name = "tagListDto", type = "TagListDto", value = "分页查询外加模糊查询的DTO, 根据标签名")
    })
    public ResponseResult list(Integer pageNum, Integer pageSize, TagListDto tagListDto){
        return tagService.pageTagList(pageNum, pageSize, tagListDto);
    }

    @PostMapping
    @SystemLog(businessName = "新增标签")
    @ApiOperation(value = "新增标签", notes = "用于后台用户新增标签信息")
    @ApiImplicitParam(name = "tagVo", type = "TagVo", value = "用于新增标签的DTO，同理和回显使用的VO相同，使用VO代替DTO")
    public ResponseResult addTag(@RequestBody TagVo tagVo){
        // 新增标签
        return tagService.addTag(tagVo);
    }

    @DeleteMapping("/{id}")
    @SystemLog(businessName = "删除标签")
    @ApiOperation(value = "删除标签", notes = "用于后台用户删除标签信息")
    @ApiImplicitParam(name = "id", type = "Long", value = "标签id值")
    public ResponseResult deleteTag(@PathVariable("id") Long id){
        // 删除标签
        return tagService.deleteTag(id);
    }

    @GetMapping("/{id}")
    @SystemLog(businessName = "根据id查询标签")
    @ApiOperation(value = "根据id查询标签", notes = "用于后台用户根据id查询标签信息，用于修改标签时，回显信息")
    @ApiImplicitParam(name = "id", type = "Long", value = "标签d值")
    public ResponseResult getTag(@PathVariable("id") Long id){
        // 根据id查询
        return tagService.getTag(id);
    }

    @PutMapping
    @SystemLog(businessName = "修改标签")
    @ApiOperation(value = "修改标签", notes = "用于后台用户修改标签")
    @ApiImplicitParam(name = "tagVo", type = "TagVo", value = "用于修改标签的DTO，同理和回显使用的VO相同，使用VO代替DTO")
    public ResponseResult updateTag(@RequestBody TagVo tagVo){
        // 修改信息
        return tagService.updateTag(tagVo);
    }

    @GetMapping("/listAllTag")
    @SystemLog(businessName = "查询所有标签")
    @ApiOperation(value = "查询所有标签", notes = "后台用户查询所有标签，用于修改文章时，回显所有标签信息")
    public ResponseResult listAllTag(){
        // 查询所有标签
        return tagService.listAllTag();
    }

}
