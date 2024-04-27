package com.xujing.service.impl;

import com.xujing.constants.SystemConstants;
import com.xujing.domain.ResponseResult;
import com.xujing.domain.entity.LoginUser;
import com.xujing.domain.entity.User;
import com.xujing.domain.vo.AdminLoginUserVo;
import com.xujing.domain.vo.BlogUserLoginVo;
import com.xujing.domain.vo.UserInfoVo;
import com.xujing.enums.AppHttpCodeEnum;
import com.xujing.service.LoginService;
import com.xujing.utils.BeanCopyUtils;
import com.xujing.utils.JwtUtil;
import com.xujing.utils.RedisCache;
import com.xujing.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author 86136
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        // 判断是否认证通过
        if(Objects.isNull(authenticate)){
            throw new RuntimeException(AppHttpCodeEnum.LOGIN_ERROR.getMsg());
        }
        // 获取userId生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);

        // 把用户信息存入redis
        redisCache.setCacheObject(SystemConstants.REDIS_ADMIN_USER +userId, loginUser);

        AdminLoginUserVo loginUserVo = new AdminLoginUserVo(jwt);
        return ResponseResult.okResult(loginUserVo);
    }

    @Override
    public ResponseResult logout() {
        Long userId = SecurityUtils.getUserId();
        redisCache.deleteObject(SystemConstants.REDIS_ADMIN_USER +userId);
        return ResponseResult.okResult();
    }
}
