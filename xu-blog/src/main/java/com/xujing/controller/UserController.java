package com.xujing.controller;

import com.xujing.annotation.SystemLog;
import com.xujing.domain.ResponseResult;
import com.xujing.domain.dto.RegisterUserDto;
import com.xujing.domain.dto.UpdateUserDto;
import com.xujing.domain.entity.User;
import com.xujing.service.UserService;
import com.xujing.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 86136
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户", description = "用户相关接口")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/userInfo")
    @SystemLog(businessName = "查询用户信息")
    @ApiOperation(value = "查询用户信息", notes = "查询用户的基本信息")
    public ResponseResult userInfo(){
        return userService.userInfo();
    }

    @PutMapping("/userInfo")
    @SystemLog(businessName = "更新用户信息")
    @ApiOperation(value = "更新用户信息", notes = "根据用户传来的信息进行更新")
    @ApiImplicitParam(name = "user", paramType = "UpdateUserDto", value = "用户需要更新的信息UpdateUserDto对象")
    public ResponseResult updateUserInfo(@RequestBody UpdateUserDto updateUserDto){
        User user = BeanCopyUtils.copyBean(updateUserDto, User.class);
        return userService.updateUserInfo(user);
    }

    @PostMapping("/register")
    @SystemLog(businessName = "新用户注册")
    @ApiOperation(value = "新用户注册", notes = "根据提交的信息，进行注册")
    @ApiImplicitParam(name = "user", paramType = "RegisterUserDto", value = "用户需要注册的信息RegisterUserDto对象")
    public ResponseResult register(@RequestBody RegisterUserDto registerUserDto){
        User user = BeanCopyUtils.copyBean(registerUserDto, User.class);
        return userService.register(user);
    }
}
