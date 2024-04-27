package com.xujing.controller;

import com.xujing.annotation.SystemLog;
import com.xujing.domain.ResponseResult;
import com.xujing.domain.dto.AdminRoleDto;
import com.xujing.domain.dto.ChangeStatusDto;
import com.xujing.domain.dto.RoleDto;
import com.xujing.service.RoleService;
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
@RequestMapping("/system/role")
@Api(tags = "角色管理", description = "角色管理相关接口")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    @SystemLog(businessName = "分页查询角色")
    @ApiOperation(value = "分页查询角色", notes = "用于后台用户分页查询角色信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", type = "Integer", value = "分页查询的当前页码"),
            @ApiImplicitParam(name = "pageSize", type = "Integer", value = "每页显示的条目数"),
            @ApiImplicitParam(name = "roleDto", type = "AdminRoleDto", value = "分页查询外加模糊查询的DTO, 1.根据角色名称 2.根据角色的状态")
    })
    public ResponseResult list(Integer pageNum, Integer pageSize, AdminRoleDto roleDto){
        // 分页查询角色
        return roleService.pageList(pageNum, pageSize, roleDto);
    }

    @GetMapping("/listAllRole")
    @SystemLog(businessName = "查询所有状态正常的角色列表")
    @ApiOperation(value = "查询所有状态正常的角色列表", notes = "用于后台用户查询状态正常的角色列表")
    public ResponseResult listAllRole(){
        // 查询角色信息，只查询未被停用的角色
        return  roleService.listAllRole();
    }

    @PutMapping("/changeStatus")
    @SystemLog(businessName = "修改角色状态")
    @ApiOperation(value = "修改角色状态", notes = "用于后台用户修改角色状态")
    @ApiImplicitParam(name = "statusDto", type = "ChangeStatusDto", value = "用于修改角色状态的DTO")
    public ResponseResult changeStatus(@RequestBody ChangeStatusDto statusDto){
        // 修改角色状态
        return roleService.changeStatus(statusDto);
    }

    @PostMapping
    @SystemLog(businessName = "添加角色")
    @ApiOperation(value = "添加角色", notes = "用于后台用户添加角色")
    @ApiImplicitParam(name = "roleDto", type = "RoleDto", value = "用于添加角色的DTO")
    public ResponseResult addRole(@RequestBody RoleDto roleDto){
        // 新增角色
        return roleService.addRole(roleDto);
    }

    @GetMapping("/{id}")
    @SystemLog(businessName = "根据id查询角色")
    @ApiOperation(value = "根据id查询角色", notes = "用于后台用户修改角色时，回显数据")
    @ApiImplicitParam(name = "id", type = "Long", value = "角色id值")
    public ResponseResult getRoleById(@PathVariable("id") Long id){
        // 根据id查询角色，用于回显数据
        return roleService.getRoleById(id);
    }

    @PutMapping
    @SystemLog(businessName = "修改角色")
    @ApiOperation(value = "修改角色", notes = "用于后台用户修改角色")
    @ApiImplicitParam(name = "roleDto", type = "RoleDto", value = "用于修改角色的DTO")
    public ResponseResult updateRole(@RequestBody RoleDto roleDto){
        // 更新角色
        return roleService.updateRole(roleDto);
    }

    @DeleteMapping("/{id}")
    @SystemLog(businessName = "删除角色")
    @ApiOperation(value = "删除角色", notes = "用于后台用户删除角色")
    @ApiImplicitParam(name = "id", type = "Long", value = "角色id值")
    public ResponseResult deleteRole(@PathVariable("id") Long id){
        // 删除角色
        return roleService.deleteRole(id);
    }
}
