package com.xujing.domain.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.xujing.domain.entity.Menu;
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
public class RoutersVo {

    @ExcelProperty("菜单列表")
    private List<Menu> menus;
}
