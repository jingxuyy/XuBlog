package com.xujing.service.impl;

import com.xujing.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 86136
 */
@Service("ps")
public class PermissionService {


    public boolean hasPermission(String permission){
        // 如果是超级管理员  直接返回true
        if(SecurityUtils.getUserId() == 1){
            return true;
        }
        // 否则获取当前登录用户所具有的权限列表 判断是否存在相应的权限
        List<String> permissions = SecurityUtils.getLoginUser().getPermissions();
        return permissions.contains(permission);
    }
}
