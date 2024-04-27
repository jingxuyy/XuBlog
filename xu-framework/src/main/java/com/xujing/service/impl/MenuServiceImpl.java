package com.xujing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xujing.constants.SystemConstants;
import com.xujing.domain.ResponseResult;
import com.xujing.domain.dto.GetMenuDto;
import com.xujing.domain.entity.Menu;
import com.xujing.domain.entity.RoleMenu;
import com.xujing.domain.vo.*;
import com.xujing.enums.AppHttpCodeEnum;
import com.xujing.exception.SystemException;
import com.xujing.mapper.MenuMapper;
import com.xujing.service.MenuService;
import com.xujing.service.RoleMenuService;
import com.xujing.service.RoleService;
import com.xujing.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author makejava
 * @since 2023-12-10 15:55:05
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public List<String> selectPermsByUserId(Long userId) {
        // 如果是管理员(id=1)，返回所有的权限
        if(userId == 1){
            LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(Menu::getMenuType, SystemConstants.BUTTON, SystemConstants.MENU);
            queryWrapper.eq(Menu::getStatus, SystemConstants.STATUS_NORMAL);
            List<Menu> menus = list(queryWrapper);
            List<String> perms = menus.stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());
            return perms;
        }
        // 否则返回当前用户所具有的权限
        return getBaseMapper().selectPermsByUserId(userId);
    }

    @Override
    public List<Menu> selectRouterMenuTreeByUserId(Long userId) {
        // 判断是否是管理员
        List<Menu> menus =null;
        if(userId==1){
            menus = getBaseMapper().selectAllRouterMenu();
        }else {
            // 否则返回当前用户的Menu
            menus = getBaseMapper().selectRouterMenuTreeByUserId(userId);
        }
        List<Menu> menuVoTree = builderMenuTree(menus, 0L);
        return menuVoTree;
    }

    @Override
    public ResponseResult getMenuList(GetMenuDto menuDto) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(menuDto.getMenuName()), Menu::getMenuName, menuDto.getMenuName());
        queryWrapper.like(StringUtils.hasText(menuDto.getStatus()), Menu::getStatus, menuDto.getStatus());
        queryWrapper.orderByAsc(Menu::getOrderNum);
        List<Menu> menus = list(queryWrapper);
        List<AdminMenuListVo> adminMenuListVos = BeanCopyUtils.copyBeanList(menus, AdminMenuListVo.class);
        return ResponseResult.okResult(adminMenuListVos);
    }

    @Override
    public ResponseResult addMenu(Menu menu) {
        save(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getMenuById(Long id) {
        Menu menu = getById(id);
        AdminUpdateMenuVo menuVo = BeanCopyUtils.copyBean(menu, AdminUpdateMenuVo.class);
        return ResponseResult.okResult(menuVo);
    }

    @Override
    public ResponseResult updateMenu(Menu menu) {
        // 修改菜单，可以设置上级菜单，所以当前菜单的上级菜单不能是自己， 并且上级菜单必须要有
        if(Objects.isNull(menu.getParentId())){
            throw new SystemException(AppHttpCodeEnum.MENU_ISNULL);
        }
        if(menu.getId().equals(menu.getParentId())){
            throw new SystemException(AppHttpCodeEnum.MENU_PARENT_ERROR);
        }
        updateById(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteMenu(Long menuId) {
        // 当前菜单存在子菜单不能删除
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getParentId, menuId);
        int count = count(queryWrapper);
        if(count>0){
            return ResponseResult.errorResult(AppHttpCodeEnum.CHILDREN_MENU);
        }
        removeById(menuId);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult treeSelect() {
        List<Menu> menus = list();
        List<MenuTreeVo> menuTreeVoList = menus.stream()
                .map(menu -> new MenuTreeVo(menu.getId(), menu.getParentId(), menu.getMenuName(), null))
                .collect(Collectors.toList());
        List<MenuTreeVo> collect = menuTreeVoList.stream()
                .filter(menusVo -> menusVo.getParentId().equals(0L))
                .map(menusVo -> menusVo.setChildren(getChildrenList(menuTreeVoList, menusVo)))
                .collect(Collectors.toList());


        return ResponseResult.okResult(collect);
    }

    @Override
    public ResponseResult getMenuTree(Long id) {
        List<String> stringList = null;

        // 查询所有菜单
        List<Menu> menus = list();
        List<MenuTreeVo> menuTreeVoList = menus.stream()
                .map(menu -> new MenuTreeVo(menu.getId(), menu.getParentId(), menu.getMenuName(), null))
                .collect(Collectors.toList());
        List<MenuTreeVo> collect = menuTreeVoList.stream()
                .filter(menusVo -> menusVo.getParentId().equals(0L))
                .map(menusVo -> menusVo.setChildren(getChildrenList(menuTreeVoList, menusVo)))
                .collect(Collectors.toList());
        // 判断是不是超级管理员
        if(id==1){
            // 是管理员则把所有权限信息都赋值
            stringList = menus.stream().map(menu -> menu.getId().toString()).collect(Collectors.toList());
        }else {
            // 不是管理员 根据角色id在 role_menu表里查询 菜单id
            LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(RoleMenu::getRoleId, id);
            List<RoleMenu> list = roleMenuService.list(queryWrapper);
            stringList = list.stream().map(roleMenu -> roleMenu.getMenuId().toString()).collect(Collectors.toList());
        }



        return ResponseResult.okResult(new AdminMenuTreeAndCheckVo(collect, stringList));
    }

    private List<Menu> builderMenuTree(List<Menu> menus, Long parentId) {
        List<Menu> menuList = menus.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> menu.setChildren(getChildren(menu, menus)))
                .collect(Collectors.toList());
        return menuList;
    }

    private List<Menu> getChildren(Menu menu, List<Menu> menus) {
        List<Menu> childrenMenu = menus.stream()
                .filter(m -> m.getParentId().equals(menu.getId()))
                .collect(Collectors.toList());
        return childrenMenu;
    }

    private List<MenuTreeVo> getChildrenList(List<MenuTreeVo> list, MenuTreeVo menuTreeVo){
        List<MenuTreeVo> collect = list.stream()
                .filter(menuVo -> menuVo.getParentId().equals(menuTreeVo.getId()))
                .map(menuVo -> menuVo.setChildren(getChildrenList(list, menuVo)))
                .collect(Collectors.toList());
        return collect;
    }
}

