package com.xujing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xujing.constants.SystemConstants;
import com.xujing.domain.ResponseResult;
import com.xujing.domain.dto.AdminRoleDto;
import com.xujing.domain.dto.ChangeStatusDto;
import com.xujing.domain.dto.RoleDto;
import com.xujing.domain.entity.Role;
import com.xujing.domain.entity.RoleMenu;
import com.xujing.domain.vo.AdminRoleListVo;
import com.xujing.domain.vo.PageVo;
import com.xujing.domain.vo.RoleVo;
import com.xujing.mapper.RoleMapper;
import com.xujing.service.RoleMenuService;
import com.xujing.service.RoleService;
import com.xujing.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2023-12-10 15:55:27
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public List<String> selectRoleKeyByUserId(Long userId) {
        // 判断是否为管理员 如果是返回集合信息只需要有admin
        if(userId == 1){
            List<String> roleKeys = new ArrayList<>();
            roleKeys.add("admin");
            return roleKeys;
        }
        return getBaseMapper().selectRoleKeyByUserId(userId);
    }

    @Override
    public ResponseResult pageList(Integer pageNum, Integer pageSize, AdminRoleDto roleVo) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(roleVo.getRoleName()), Role::getRoleName, roleVo.getRoleName());
        queryWrapper.eq(StringUtils.hasText(roleVo.getStatus()), Role::getStatus, roleVo.getStatus());
        queryWrapper.orderByAsc(Role::getRoleSort);

        Page<Role> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);

        if(page.getRecords().size()==0 && pageNum>1){
            page.setCurrent(pageNum-1);
            page(page, queryWrapper);
        }

        PageVo pageVo = new PageVo(BeanCopyUtils.copyBeanList(page.getRecords(), AdminRoleListVo.class), page.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult changeStatus(ChangeStatusDto statusDto) {
        Role role = new Role();
        role.setId(Long.valueOf(statusDto.getRoleId()));
        role.setStatus(statusDto.getStatus());
        updateById(role);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult addRole(RoleDto roleDto) {
        Role role = BeanCopyUtils.copyBean(roleDto, Role.class);
        save(role);

        List<RoleMenu> collect = roleDto.getMenuIds()
                .stream()
                .map(ids -> new RoleMenu(role.getId(), Long.valueOf(ids)))
                .collect(Collectors.toList());
        roleMenuService.saveBatch(collect);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getRoleById(Long id) {
        Role role = getById(id);
        RoleVo roleVo = BeanCopyUtils.copyBean(role, RoleVo.class);
        return ResponseResult.okResult(roleVo);
    }

    @Override
    @Transactional
    public ResponseResult updateRole(RoleDto roleDto) {
        // 修改Role表
        Role role = BeanCopyUtils.copyBean(roleDto, Role.class);
        updateById(role);
        // 删除 role_menu表里对应当前role_id对应的字段
        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMenu::getRoleId, roleDto.getId());
        roleMenuService.remove(queryWrapper);
        // 将新的role_menu关系添加
        List<RoleMenu> collect = roleDto.getMenuIds().stream()
                .map(menuId -> new RoleMenu(role.getId(), Long.valueOf(menuId)))
                .collect(Collectors.toList());
        roleMenuService.saveBatch(collect);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteRole(Long id) {
        removeById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listAllRole() {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getStatus, SystemConstants.STATUS_NORMAL);
        List<Role> roleList = list(queryWrapper);
        return ResponseResult.okResult(roleList);
    }


}

