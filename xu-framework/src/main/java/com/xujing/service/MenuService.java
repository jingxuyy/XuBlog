package com.xujing.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xujing.domain.ResponseResult;
import com.xujing.domain.dto.GetMenuDto;
import com.xujing.domain.entity.Menu;
import com.xujing.domain.vo.MenuVo;

import java.util.List;


/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author makejava
 * @since 2023-12-10 15:55:05
 */
public interface MenuService extends IService<Menu> {

    List<String> selectPermsByUserId(Long userId);

    List<Menu> selectRouterMenuTreeByUserId(Long userId);

    ResponseResult getMenuList(GetMenuDto menuDto);

    ResponseResult addMenu(Menu menu);

    ResponseResult getMenuById(Long id);

    ResponseResult updateMenu(Menu menu);

    ResponseResult deleteMenu(Long menuId);

    ResponseResult treeSelect();

    ResponseResult getMenuTree(Long id);
}

