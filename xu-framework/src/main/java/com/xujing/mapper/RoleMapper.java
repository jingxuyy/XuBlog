package com.xujing.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xujing.domain.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 角色信息表(Role)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-10 15:55:27
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRoleKeyByUserId(@Param("userId") Long userId);
}

