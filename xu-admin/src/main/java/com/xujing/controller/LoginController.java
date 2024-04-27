package com.xujing.controller;

import com.xujing.annotation.SystemLog;
import com.xujing.domain.ResponseResult;
import com.xujing.domain.dto.UserLoginDto;
import com.xujing.domain.entity.LoginUser;
import com.xujing.domain.entity.Menu;
import com.xujing.domain.entity.User;
import com.xujing.domain.vo.AdminUserInfoVo;
import com.xujing.domain.vo.MenuVo;
import com.xujing.domain.vo.RoutersVo;
import com.xujing.domain.vo.UserInfoVo;
import com.xujing.service.LoginService;
import com.xujing.service.MenuService;
import com.xujing.service.RoleService;
import com.xujing.utils.BeanCopyUtils;
import com.xujing.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 86136
 */
@RestController
@Api(tags = "登录-退出", description = "后台用户登录-退出-授权相关接口")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;

    @PostMapping("/user/login")
    @SystemLog(businessName = "后台用户登录")
    @ApiOperation(value = "后台用户登录", notes = "用于后台用户登录")
    @ApiImplicitParam(name = "userLoginDto", type = "UserLoginDto", value = "用户登录的DTO")
    public ResponseResult login(@RequestBody UserLoginDto userLoginDto){
        User user = BeanCopyUtils.copyBean(userLoginDto, User.class);
        return loginService.login(user);
    }

    @GetMapping("/getInfo")
    @SystemLog(businessName = "查询用户信息")
    @ApiOperation(value = "查询用户信息", notes = "用于后台登录时查询用户信息，1.用户信息 2.用户权限信息 3.用户的角色信息")
    public ResponseResult getInfo(){
        // 获取当前登录的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        // 根据用户id查询权限信息
        List<String>  perms = menuService.selectPermsByUserId(SecurityUtils.getUserId());
        // 根据用户id查询角色信息
        List<String> roleKeyList = roleService.selectRoleKeyByUserId(SecurityUtils.getUserId());

        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);

        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(userInfoVo, perms, roleKeyList);
        return ResponseResult.okResult(adminUserInfoVo);
    }

    @GetMapping("/getRouters")
    @SystemLog(businessName = "查询路由信息")
    @ApiOperation(value = "查询路由信息", notes = "用于后台用户登录时，根据权限和角色信息，展示不同的路由")
    public ResponseResult getRouters(){
        Long userId = SecurityUtils.getUserId();
        List<Menu> menu = menuService.selectRouterMenuTreeByUserId(userId);
        return ResponseResult.okResult(new RoutersVo(menu));
    }

    @PostMapping("/user/logout")
    @SystemLog(businessName = "用户退出")
    @ApiOperation(value = "用户退出", notes = "用于后台用户退出")
    public ResponseResult logout(){
        return loginService.logout();
    }





}
