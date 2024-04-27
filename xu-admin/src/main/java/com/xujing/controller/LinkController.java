package com.xujing.controller;

import com.xujing.annotation.SystemLog;
import com.xujing.domain.ResponseResult;
import com.xujing.domain.dto.LinkDto;
import com.xujing.domain.dto.LinkStatusDto;
import com.xujing.domain.vo.LinkVo;
import com.xujing.service.LinkService;
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
@RequestMapping("/content/link")
@Api(tags = "友链管理", description = "友链管理相关接口")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @GetMapping("/list")
    @SystemLog(businessName = "分页查询友链")
    @ApiOperation(value = "分页查询友链", notes = "用于后台用户分页查询友链，默认每页10条记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", type = "Integer", value = "分页查询的当前页码"),
            @ApiImplicitParam(name = "pageSize", type = "Integer", value = "每页显示的条目数"),
            @ApiImplicitParam(name = "linkDto", type = "LinkDto", value = "分页查询外加模糊查询的DTO, 1.根据友链名称 2.根据友链的状态")
    })
    public ResponseResult getLinkList(Integer pageNum, Integer pageSize, LinkDto linkDto){
        // 友链分页查询
        return linkService.getLinkList(pageNum, pageSize, linkDto);
    }

    @PostMapping
    @SystemLog(businessName = "添加友链")
    @ApiOperation(value = "添加友链", notes = "用于后台用户添加友链")
    @ApiImplicitParam(name = "linkVo", type = "LinkVo", value = "添加友链的VO, 同理存在VO和DTO相同，因此使用DTO代替VO")
    public ResponseResult addLink(@RequestBody LinkVo linkVo){
        // 新增友链
        return linkService.addLink(linkVo);
    }

    @GetMapping("/{id}")
    @SystemLog(businessName = "根据id查询友链")
    @ApiOperation(value = "根据id查询友链", notes = "用于后台用户根据id查询友链，用于修改友链时，回显数据")
    @ApiImplicitParam(name = "id", type = "Long", value = "友链id值")
    public ResponseResult getLinkById(@PathVariable("id") Long id){
        // 根据id查询友联，用于回显数据
        return linkService.getLinkById(id);
    }

    @PutMapping
    @SystemLog(businessName = "修改友链")
    @ApiOperation(value = "修改友链", notes = "用于后台用户修改友联")
    @ApiImplicitParam(name = "linkVo", type = "LinkVo", value = "修改友链的VO, 同理存在VO和DTO相同，因此使用DTO代替VO")
    public ResponseResult updateLink(@RequestBody LinkVo linkVo){
        // 修改友链信息
        return linkService.updateLink(linkVo);
    }

    @DeleteMapping("/{id}")
    @SystemLog(businessName = "删除友链")
    @ApiOperation(value = "删除友链", notes = "用于后台用户删除友链")
    @ApiImplicitParam(name = "id", type = "Long", value = "友链id值")
    public ResponseResult deleteLink(@PathVariable("id") Long id){
        // 删除友链
        return linkService.deleteLink(id);
    }

    @PutMapping("/changeLinkStatus")
    @SystemLog(businessName = "友链审核")
    @ApiOperation(value = "友联审核", notes = "用于后台用户审核当前友链")
    @ApiImplicitParam(name = "linkStatusDto", type = "LinkStatusDto", value = "审核友链的DTO 1.id 2.状态")
    public ResponseResult changeLinkStatus(@RequestBody LinkStatusDto linkStatusDto){
        // 审核友链
        return linkService.changeLinkStatus(linkStatusDto);
    }
}
