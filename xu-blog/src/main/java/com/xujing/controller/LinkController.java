package com.xujing.controller;

import com.xujing.annotation.SystemLog;
import com.xujing.domain.ResponseResult;
import com.xujing.service.LinkService;
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
@RequestMapping("/link")
@Api(tags = "友链", description = "友链相关接口")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @GetMapping("/getAllLink")
    @SystemLog(businessName = "查询友联信息")
    @ApiOperation(value = "查询友联信息", notes = "查询所有的友链信息")
    public ResponseResult getAllLink(){
        return linkService.getAllLink();
    }
}
