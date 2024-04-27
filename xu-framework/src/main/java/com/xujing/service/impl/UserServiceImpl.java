package com.xujing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xujing.domain.ResponseResult;
import com.xujing.domain.dto.AddUserDto;
import com.xujing.domain.dto.UserListDto;
import com.xujing.domain.dto.UserUpdateDto;
import com.xujing.domain.entity.Role;
import com.xujing.domain.entity.User;
import com.xujing.domain.entity.UserRole;
import com.xujing.domain.vo.AdminUpdateUserVo;
import com.xujing.domain.vo.PageVo;
import com.xujing.domain.vo.UserInfoVo;
import com.xujing.enums.AppHttpCodeEnum;
import com.xujing.exception.SystemException;
import com.xujing.mapper.UserMapper;
import com.xujing.service.RoleService;
import com.xujing.service.UserRoleService;
import com.xujing.service.UserService;
import com.xujing.utils.BeanCopyUtils;
import com.xujing.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 86136
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;



    @Override
    public ResponseResult userInfo() {
        // 获取当前用户id
        Long userId = SecurityUtils.getUserId();
        // 根据用户id查询用户信息
        User user = getById(userId);
        // 封装成UserInfoVo
        UserInfoVo vo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        boolean b = updateById(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult register(User user) {
        // 对数据进行非空判断
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        if(!StringUtils.hasText(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USER_NICKNAME);
        }
        if(!StringUtils.hasText(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_PASSWORD);
        }
        if(!StringUtils.hasText(user.getEmail())){
            throw new SystemException((AppHttpCodeEnum.REQUIRE_EMAIL));
        }
        // 对数据进行是否存在判断
        if(userNameExist(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if(userNickNameExist(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.USER_NICK_NAME_EXIST);
        }
        if(emailExist(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        // 对密码进行加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // 存入数据库
        save(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getUserList(Integer pageNum, Integer pageSize, UserListDto userListDto) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(userListDto.getUserName()), User::getUserName, userListDto.getUserName());
        queryWrapper.eq(StringUtils.hasText(userListDto.getPhonenumber()), User::getPhonenumber, userListDto.getPhonenumber());
        queryWrapper.eq(StringUtils.hasText(userListDto.getStatus()), User::getStatus, userListDto.getStatus());

        Page<User> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);

        if(page.getRecords().size()==0 && pageNum>1){
            page.setCurrent(pageNum-1);
            page(page, queryWrapper);
        }

        PageVo pageVo = new PageVo(page.getRecords(), page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    @Transactional
    public ResponseResult addUser(AddUserDto addUserDto) {
        // 1. 用户名不能为空，且唯一
        if(!StringUtils.hasText(addUserDto.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        if(userNameExist(addUserDto.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }

        // 2. 昵称不能为空，且唯一
        if(!StringUtils.hasText(addUserDto.getNickName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USER_NICKNAME);
        }
        if(userNickNameExist(addUserDto.getNickName())){
            throw new SystemException(AppHttpCodeEnum.USER_NICK_NAME_EXIST);
        }

        // 3. 密码不能为空
        if(!StringUtils.hasText(addUserDto.getPassword())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_PASSWORD);
        }

        // 4. 手机号不能为空，且唯一
        if(!StringUtils.hasText(addUserDto.getPhonenumber())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_PHONE_NUMBER);
        }
        if(phoneNumberExist(addUserDto.getPhonenumber())){
            throw new SystemException(AppHttpCodeEnum.PHONE_NUMBER_EXIST);
        }

        // 5. 邮箱不能为空，且唯一
        if(!StringUtils.hasText(addUserDto.getEmail())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_EMAIL);
        }
        if(emailExist(addUserDto.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }

        User user = BeanCopyUtils.copyBean(addUserDto, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        save(user);

        List<UserRole> collect = addUserDto.getRoleIds().stream()
                .map(userRole -> new UserRole(user.getId(), Long.valueOf(userRole)))
                .collect(Collectors.toList());

        userRoleService.saveBatch(collect);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteUser(Long id) {
        removeById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getUserById(Long id) {
        User user = getById(id);
        // 还要查询 当前用户所关联的角色id列表， 以及所有角色
        // 1. 根据用户id 查询角色id
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId, id);
        queryWrapper.select(UserRole::getRoleId);
        List<UserRole> userRoles = userRoleService.list(queryWrapper);
        List<String> rolesIds = userRoles.stream()
                .map(userRole -> userRole.getRoleId().toString())
                .collect(Collectors.toList());
        // 2. 查询所有角色列表
        List<Role> roleList = roleService.list();
        return ResponseResult.okResult(new AdminUpdateUserVo(roleList, rolesIds, user));
    }

    @Transactional
    @Override
    public ResponseResult updateUser(UserUpdateDto updateDto) {
        // 1. 更改用户信息
        User user = BeanCopyUtils.copyBean(updateDto, User.class);
        updateById(user);

        // 2. 更改用户角色对应信息
        // 2.1 删除原始用户角色对应信息
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId, updateDto.getId());
        userRoleService.remove(queryWrapper);
        // 2.2 创建新的用户角色对应信息
        List<UserRole> userRoleList = updateDto.getRoleIds().stream()
                .map(roleId -> new UserRole(updateDto.getId(), Long.valueOf(roleId)))
                .collect(Collectors.toList());
        userRoleService.saveBatch(userRoleList);
        return ResponseResult.okResult();
    }

    /**
     * 根据用户名判断数据库是否存在
     */
    private boolean userNameExist(String userName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, userName);
        User one = getOne(queryWrapper);
        return Objects.nonNull(one);
    }

    /**
     * 根据昵称判断数据库是否存在
     */
    private boolean userNickNameExist(String userNickName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getNickName, userNickName);
        User one = getOne(queryWrapper);
        return Objects.nonNull(one);
    }

    /**
     * 根据邮箱判断数据库是否存在
     */
    private boolean emailExist(String email) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail, email);
        User one = getOne(queryWrapper);
        return Objects.nonNull(one);
    }

    /**
     * 根据手机号判断数据库是否存在
     */
    private boolean phoneNumberExist(String phoneNumber) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhonenumber, phoneNumber);
        User one = getOne(queryWrapper);
        return Objects.nonNull(one);
    }
}
