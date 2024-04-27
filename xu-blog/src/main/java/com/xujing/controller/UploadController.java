package com.xujing.controller;

import com.xujing.annotation.SystemLog;
import com.xujing.domain.ResponseResult;
import com.xujing.service.UploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 86136
 */
@RestController
@Api(tags = "文件上传", description = "用户头像上传相关接口")
public class UploadController {
    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload")
    @ApiOperation(value = "用户头像上传", notes = "将用户选择的头像上传到七牛云OSS中")
    @ApiImplicitParam(name = "img", paramType = "MultipartFile", value = "用户上传的图片对象")
    public ResponseResult uploadImg(MultipartFile img){
        return uploadService.uploadImg(img);
    }


}
