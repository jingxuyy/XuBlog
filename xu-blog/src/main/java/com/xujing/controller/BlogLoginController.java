package com.xujing.controller;

import com.xujing.annotation.SystemLog;
import com.xujing.domain.ResponseResult;
import com.xujing.domain.dto.UserLoginDto;
import com.xujing.domain.entity.User;
import com.xujing.service.BlogLoginService;
import com.xujing.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 86136
 */
@RestController
@Api(tags = "登录-退出", description = "登录-退出相关接口")
public class BlogLoginController {

    @Autowired
    private BlogLoginService blogLoginService;

    @PostMapping("/login")
    @SystemLog(businessName = "用户登录")
    @ApiOperation(value = "用户登录", notes = "根据用户提供的信息进行登录操作")
    @ApiImplicitParam(name = "user", paramType = "UserLoginDto", value = "用户登录的信息UserLoginDto对象")
    public ResponseResult login(@RequestBody UserLoginDto userLoginDto){
        User user = BeanCopyUtils.copyBean(userLoginDto, User.class);
        return blogLoginService.login(user);
    }

    @PostMapping("/logout")
    @SystemLog(businessName = "用户退出")
    @ApiOperation(value = "用户退出", notes = "将当前用户退出，设置为未登录状态")
    public ResponseResult logout(){
        return blogLoginService.logout();
    }
}
