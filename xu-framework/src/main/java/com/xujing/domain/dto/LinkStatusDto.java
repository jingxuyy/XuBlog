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
public class LinkStatusDto {

    @ExcelProperty("友链id")
    private Long id;

    @ExcelProperty("审核状态 (0代表审核通过，1代表审核未通过，2代表未审核)")
    private String status;
}
