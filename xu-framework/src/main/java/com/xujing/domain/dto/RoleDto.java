package com.xujing.domain.dto;

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
public class RoleDto {

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

    @ExcelProperty("菜单id列表")
    private List<String> menuIds;
}
