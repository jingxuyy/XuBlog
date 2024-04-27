package com.xujing.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xujing.domain.ResponseResult;
import com.xujing.domain.dto.AddUserDto;
import com.xujing.domain.dto.UserListDto;
import com.xujing.domain.dto.UserUpdateDto;
import com.xujing.domain.entity.User;

/**
 * @author 86136
 */
public interface UserService extends IService<User> {
    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);

    ResponseResult getUserList(Integer pageNum, Integer pageSize, UserListDto userListDto);

    ResponseResult addUser(AddUserDto addUserDto);

    ResponseResult deleteUser(Long id);

    ResponseResult getUserById(Long id);

    ResponseResult updateUser(UserUpdateDto updateDto);
}
