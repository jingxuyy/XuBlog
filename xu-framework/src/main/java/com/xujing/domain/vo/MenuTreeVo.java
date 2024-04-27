package com.xujing.domain.vo;


import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author 86136
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class MenuTreeVo {

    @ExcelProperty("菜单id")
    private Long id;

    @ExcelProperty("父菜单ID")
    private Long parentId;

    @ExcelProperty("菜单名称")
    private String label;

    @ExcelProperty("子菜单")
    private List<MenuTreeVo> children;
}
