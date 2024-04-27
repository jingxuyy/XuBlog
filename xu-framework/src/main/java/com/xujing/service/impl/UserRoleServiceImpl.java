package com.xujing.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xujing.domain.entity.UserRole;
import com.xujing.mapper.UserRoleMapper;
import com.xujing.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * 用户和角色关联表(UserRole)表服务实现类
 *
 * @author makejava
 * @since 2023-12-13 19:23:59
 */
@Service("userRoleService")
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}

