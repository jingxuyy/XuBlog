package com.xujing.controller;

import com.xujing.annotation.SystemLog;
import com.xujing.domain.ResponseResult;
import com.xujing.domain.dto.GetMenuDto;
import com.xujing.domain.entity.Menu;
import com.xujing.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 86136
 */
@RestController
@RequestMapping("/system/menu")
@Api(tags = "菜单管理", description = "后台菜单管理相关接口")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/list")
    @SystemLog(businessName = "查询菜单信息列表")
    @ApiOperation(value = "查询菜单信息列表", notes = "用于后台用户查询菜单信息列表")
    @ApiImplicitParam(name = "menuDto", type = "GetMenuDto", value = "用于菜单查询的DTO条件 1.菜单名称 2.菜单状态")
    public ResponseResult getAllMenu(GetMenuDto menuDto){
        // 查询菜单列表
        return menuService.getMenuList(menuDto);
    }

    @PostMapping
    @SystemLog(businessName = "添加菜单")
    @ApiOperation(value = "添加菜单", notes = "用于后台用户添加菜单")
    @ApiImplicitParam(name = "menu", type = "Menu", value = "用于添加菜单的类，由于需要的信息和Menu相同，因此使用整个类代替DTO")
    public ResponseResult addMenu(@RequestBody Menu menu){
        // 添加菜单
        return menuService.addMenu(menu);
    }

    @GetMapping("/{id}")
    @SystemLog(businessName = "根据id查询对应菜单")
    @ApiOperation(value = "根据id查询对应菜单", notes = "用于后台用户根据id查询对应菜单，用于修改菜单时，回显数据")
    @ApiImplicitParam(name = "id", type = "Long", value = "菜单id值")
    public ResponseResult getMenuById(@PathVariable("id") Long id){
        // 根据id获取菜单
        return menuService.getMenuById(id);
    }

    @PutMapping
    @SystemLog(businessName = "修改菜单")
    @ApiOperation(value = "修改菜单", notes = "用于后台用户修改菜单")
    @ApiImplicitParam(name = "menu", type = "Menu", value = "用于修改菜单的类，由于需要的信息和Menu相同，因此使用整个类代替DTO")
    public ResponseResult updateMenu(@RequestBody Menu menu){
        // 修改菜单
        return menuService.updateMenu(menu);
    }

    @DeleteMapping("/{menuId}")
    @SystemLog(businessName = "删除菜单")
    @ApiOperation(value = "删除菜单", notes = "用于后台用户删除菜单")
    @ApiImplicitParam(name = "menuId", type = "Long", value = "菜单id值")
    public ResponseResult deleteMenu(@PathVariable("menuId") Long menuId){
        // 删除菜单
        return menuService.deleteMenu(menuId);
    }

    @GetMapping("/treeselect")
    @SystemLog(businessName = "查询菜单树")
    @ApiOperation(value = "查询菜单树", notes = "用于后台用户查询菜单树，展现菜单与菜单之间的子父级关系，用于修改菜单时，回显所有菜单层级关系")
    public ResponseResult treeSelect(){
        // 获取菜单树
        return menuService.treeSelect();
    }

    @GetMapping("/roleMenuTreeselect/{id}")
    @SystemLog(businessName = "根据角色id查询对应拥有的菜单树")
    @ApiOperation(value = "根据角色id查询对应拥有的菜单树", notes = "用于后台用户修改角色时，回显当前角色与菜单树哪些菜单有关联")
    @ApiImplicitParam(name = "id", type = "Long", value = "角色id值，根据角色查询，该角色拥有的菜单")
    public ResponseResult getMenuTree(@PathVariable("id") Long id){
        return menuService.getMenuTree(id);
    }
}
