package com.xujing.controller;

import com.xujing.annotation.SystemLog;
import com.xujing.domain.ResponseResult;
import com.xujing.domain.dto.AddUserDto;
import com.xujing.domain.dto.UserListDto;
import com.xujing.domain.dto.UserUpdateDto;
import com.xujing.service.UserService;
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
@RequestMapping("system/user")
@Api(tags = "用户管理", description = "用户管理相关接口")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    @SystemLog(businessName = "用户分页查询")
    @ApiOperation(value = "用户分页查询", notes = "用于后台用户分页查询，默认每页显示10条数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", type = "Integer", value = "分页查询的当前页码"),
            @ApiImplicitParam(name = "pageSize", type = "Integer", value = "每页显示的条目数"),
            @ApiImplicitParam(name = "userListDto", type = "UserListDto", value = "分页查询外加模糊查询的DTO, 1.根据用户名 2.根据手机号 3.根据用户状态")
    })
    public ResponseResult getUserList(Integer pageNum, Integer pageSize, UserListDto userListDto){
        // 用户分页查询
        return userService.getUserList(pageNum, pageSize, userListDto);
    }

    @PostMapping
    @SystemLog(businessName = "添加用户")
    @ApiOperation(value = "添加用户", notes = "用于后台用户添加新用户")
    @ApiImplicitParam(name = "addUserDto", type = "AddUserDto", value = "添加用户的DTO")
    public ResponseResult addUser(@RequestBody AddUserDto addUserDto){
        // 添加用户
        return userService.addUser(addUserDto);
    }

    @DeleteMapping("/{id}")
    @SystemLog(businessName = "删除用户")
    @ApiOperation(value = "删除用户", notes = "用于后台用户删除用户")
    @ApiImplicitParam(name = "id", type = "Long", value = "用户id值")
    public ResponseResult deleteUser(@PathVariable("id") Long id){
        // 删除用户
        return userService.deleteUser(id);
    }

    @GetMapping("/{id}")
    @SystemLog(businessName = "根据id查询用户")
    @ApiOperation(value = "根据id查询用户", notes = "后台用户根据id查询用户，用于修改用户时，回显数据")
    @ApiImplicitParam(name = "id", type = "Long", value = "用户id值")
    public ResponseResult getUserById(@PathVariable("id") Long id){
        // 根据id查询用户 用于修改用户的回显
        return userService.getUserById(id);
    }

    @PutMapping
    @SystemLog(businessName = "修改用户")
    @ApiOperation(value = "修改用户", notes = "用于后台用户修改用户")
    @ApiImplicitParam(name = "updateDto", type = "UserUpdateDto", value = "修改用户的DTO")
    public ResponseResult updateUser(@RequestBody UserUpdateDto updateDto){
        // 修改用户
        return userService.updateUser(updateDto);
    }
}
