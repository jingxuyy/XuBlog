package com.xujing.domain.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 86136
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleVo {

    @ExcelProperty("角色id")
    private Long id;

    @ExcelProperty("角色名称")
    private String roleName;

    @ExcelProperty("角色权限字符串")
    private String roleKey;

    @ExcelProperty("显示顺序")
    private Integer roleSort;

    @ExcelProperty("角色状态（0正常 1停用）")
    private String status;

    @ExcelProperty("备注")
    private String remark;
}
