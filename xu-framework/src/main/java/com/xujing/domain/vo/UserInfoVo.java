package com.xujing.domain.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * @author 86136
 */
@Data
@Accessors(chain = true)
public class UserInfoVo {

    @ExcelProperty("用户id")
    private Long id;

    @ExcelProperty("昵称")
    private String nickName;

    @ExcelProperty("邮箱")
    private String email;

    @ExcelProperty("用户性别（0男，1女，2未知）")
    private String sex;

    @ExcelProperty("头像")
    private String avatar;

}
