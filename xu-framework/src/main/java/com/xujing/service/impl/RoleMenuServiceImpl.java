package com.xujing.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xujing.domain.entity.RoleMenu;
import com.xujing.mapper.RoleMenuMapper;
import com.xujing.service.RoleMenuService;
import org.springframework.stereotype.Service;

/**
 * 角色和菜单关联表(RoleMenu)表服务实现类
 *
 * @author makejava
 * @since 2023-12-13 14:43:59
 */
@Service("roleMenuService")
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

}

