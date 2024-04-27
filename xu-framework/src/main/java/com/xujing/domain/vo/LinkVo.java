package com.xujing.domain.vo;

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
public class LinkVo {

    @ExcelProperty("友链id")
    private Long id;

    @ExcelProperty("友链名称")
    private String name;

    @ExcelProperty("友链logo")
    private String logo;

    @ExcelProperty("友链描述")
    private String description;

    @ExcelProperty("网站地址")
    private String address;

    @ExcelProperty("审核状态 (0代表审核通过，1代表审核未通过，2代表未审核)")
    private String status;
}
