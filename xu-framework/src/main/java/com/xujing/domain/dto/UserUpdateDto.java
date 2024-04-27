package com.xujing.domain.dto;;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 86136
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDto {

    @ExcelProperty("用户id")
    private Long id;

    @ExcelProperty("昵称")
    private String nickName;

    @ExcelProperty("账号状态（0正常 1停用）")
    private String status;

    @ExcelProperty("邮箱")
    private String email;

    @ExcelProperty("手机号")
    private String phonenumber;

    @ExcelProperty("用户性别（0男，1女，2未知）")
    private String sex;

    @ExcelProperty("角色id列表")
    private List<String> roleIds;

    @ExcelProperty("用户名")
    private String userName;
}
