package com.xujing.domain.vo;

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
public class AdminMenuTreeAndCheckVo {

    @ExcelProperty("菜单集合")
    private List<MenuTreeVo> menus;
    @ExcelProperty("权限集合")
    private List<String> checkedKeys;
}
