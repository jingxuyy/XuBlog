package com.xujing.service;

import com.xujing.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 86136
 */
public interface UploadService {
    ResponseResult uploadImg(MultipartFile img);
}
