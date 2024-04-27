package com.xujing.domain.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 86136
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserListDto {

    @ExcelProperty("用户名")
    private String userName;

    @ExcelProperty("手机号")
    private String phonenumber;

    @ExcelProperty("账号状态（0正常 1停用）")
    private String status;

}
