package com.xujing.service;

import com.xujing.domain.ResponseResult;
import com.xujing.domain.entity.User;

/**
 * @author 86136
 */
public interface LoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
