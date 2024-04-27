package com.xujing.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xujing.domain.ResponseResult;
import com.xujing.domain.dto.AdminRoleDto;
import com.xujing.domain.dto.ChangeStatusDto;
import com.xujing.domain.dto.RoleDto;
import com.xujing.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author makejava
 * @since 2023-12-10 15:55:27
 */
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long userId);

    ResponseResult pageList(Integer pageNum, Integer pageSize, AdminRoleDto roleVo);

    ResponseResult changeStatus(ChangeStatusDto statusDto);

    ResponseResult addRole(RoleDto roleDto);

    ResponseResult getRoleById(Long id);

    ResponseResult updateRole(RoleDto roleDto);

    ResponseResult deleteRole(Long id);

    ResponseResult listAllRole();
}

