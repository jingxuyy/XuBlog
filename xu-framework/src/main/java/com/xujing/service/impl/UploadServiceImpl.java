package com.xujing.service.impl;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.xujing.constants.SystemConstants;
import com.xujing.domain.ResponseResult;
import com.xujing.enums.AppHttpCodeEnum;
import com.xujing.exception.SystemException;
import com.xujing.service.UploadService;
import com.xujing.utils.PathUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

/**
 * @author 86136
 */
@Service
@ConfigurationProperties(prefix = "oss")
@Data
public class UploadServiceImpl implements UploadService {

    private String accessKey;
    private String secretKey;
    private String bucket;
    private String link;

    @Override
    public ResponseResult uploadImg(MultipartFile img) {
        // 判断文件类或者文件大小
        // 获取原始文件名 bmp,jpeg,png,gif
        String originalFilename = img.getOriginalFilename();
        if(!originalFilename.endsWith(SystemConstants.IMG_JPG) && !originalFilename.endsWith(SystemConstants.IMG_PNG)){
            throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
        }
        // 通过，上传到OSS
        String filename = PathUtils.generateFilePath(originalFilename);
        String url = uploadOSS(img, filename);

        return ResponseResult.okResult(url);
    }


    private String uploadOSS(MultipartFile img, String filepath){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;
        // 指定分片上传版本
        //...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传


        //默认不指定key的情况下，以文件内容的hash值作为文件名

        try {
            InputStream inputStream = img.getInputStream();

            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);

            try {
                Response response = uploadManager.put(inputStream, filepath,upToken,null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                return link+filepath;
            } catch (QiniuException ex) {
                ex.printStackTrace();
                if (ex.response != null) {
                    System.err.println(ex.response);

                    try {
                        String body = ex.response.toString();
                        System.err.println(body);
                    } catch (Exception ignored) {
                    }
                }
            }
        } catch (Exception ex) {
            //ignore
        }
        return SystemConstants.UPLOAD_ERROR;

    }
}
