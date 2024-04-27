package com.xujing.enums;

/**
 * @author 86136
 */

public enum AppHttpCodeEnum {
    // 成功
    SUCCESS(200, "操作成功"),
    // 登录
    NEED_LOGIN(401, "需要登陆后操作"),
    NO_OPERATOR_AUTH(403, "无权限操作"),
    SYSTEM_ERROR(500, "出现错误"),
    CHILDREN_MENU(500, "存在子菜单不允许删除"),
    USERNAME_EXIST(501, "用户名已存在"),
    PHONE_NUMBER_EXIST(502, "手机号已存在"),
    EMAIL_EXIST(503, "邮箱已存在"),
    REQUIRE_USERNAME(504, "必须填写用户名"),
    REQUIRE_PHONE_NUMBER(504, "必须填写手机号"),
    REQUIRE_PASSWORD(505, "必须填写密码"),
    REQUIRE_USER_NICKNAME(509, "必须填写昵称"),
    REQUIRE_EMAIL(508, "必须填写邮箱"),
    LOGIN_ERROR(510, "用户名或密码错误"),
    FILE_TYPE_ERROR(507, "文件类型错误,请上传bmp,jpeg,png,gif格式文件"),
    USER_NICK_NAME_EXIST(511, "昵称已存在"),
    MENU_ISNULL(512, "上级菜单不能为空"),
    MENU_PARENT_ERROR(512, "上级菜单不能是自己"),
    CONTENT_NOT_NULL(506, "评论内容不能为空");

    int code;
    String msg;

    AppHttpCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
