package com.xujing.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xujing.domain.entity.Menu;
import com.xujing.domain.vo.MenuVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 菜单权限表(Menu)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-10 15:55:03
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<String> selectPermsByUserId(@Param("userId") Long userId);

    List<Menu> selectAllRouterMenu();

    List<Menu> selectRouterMenuTreeByUserId(@Param("userId") Long userId);
}

