package com.xujing.domain.vo;

import com.alibaba.excel.annotation.ExcelProperty;
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
public class AdminUpdateMenuVo {

    @ExcelProperty("菜单id")
    private Long id;

    @ExcelProperty("菜单名称")
    private String menuName;

    @ExcelProperty("父菜单ID")
    private Long parentId;

    @ExcelProperty("显示顺序")
    private Integer orderNum;

    @ExcelProperty("路由地址")
    private String path;

    @ExcelProperty("菜单类型（M目录 C菜单 F按钮）")
    private String menuType;

    @ExcelProperty("菜单状态（0显示 1隐藏）")
    private String visible;

    @ExcelProperty("菜单状态（0正常 1停用）")
    private String status;

    @ExcelProperty("菜单图标")
    private String icon;

    @ExcelProperty("备注")
    private String remark;

}
