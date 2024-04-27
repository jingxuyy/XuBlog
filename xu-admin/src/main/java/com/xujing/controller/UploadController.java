package com.xujing.controller;

import com.xujing.annotation.SystemLog;
import com.xujing.domain.ResponseResult;
import com.xujing.service.UploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author 86136
 */
@RestController
@Api(tags = "文件上传", description = "文件上传相关接口")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload")
    @SystemLog(businessName = "文件上传")
    @ApiOperation(value = "文件上传", notes = "用于后台用户写博文，上传图片")
    @ApiImplicitParam(name = "img", type = "MultipartFile", value = "用于上传文件的对象")
    public ResponseResult uploadImg(@RequestParam("img") MultipartFile img){
        return uploadService.uploadImg(img);
    }
}
