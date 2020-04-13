package com.changgou.controller;

import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.file.FastDfsUtil;
import com.changgou.file.FileBean;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.csource.common.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@Api("文件上传接口")
@Slf4j
public class FileController {

    @PostMapping("upload")
    public Result upload(@RequestParam @SpringQueryMap MultipartFile file) throws IOException, MyException {
        FileBean fileBean = new FileBean(file.getBytes(), StringUtils.getFilenameExtension(file.getOriginalFilename()), file.getName());
        String[] upload = FastDfsUtil.upload(fileBean);
        return new Result(true, StatusCode.OK,upload[0]+upload[1]);
    }

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    //将所有yml配置文内容以key-value形式取出
    @Autowired
    private Environment environment;

    @PostMapping("upload/v2")
    public Result uploadV2(@RequestParam MultipartFile multipartFile) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();
        String filenameExtension = StringUtils.getFilenameExtension(originalFilename);
        StorePath storePath = fastFileStorageClient.uploadFile(multipartFile.getInputStream(), multipartFile.getSize(), filenameExtension, null);
        String fullPath = storePath.getFullPath();
        //拼接真实的路径  192.168.205.128:8080/MO0/xxx/xxx.jpg
        String path = environment.getProperty("nginx.url")+fullPath;
        //获取上下文
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String requestURI = request.getRequestURI();

        return new Result(true,StatusCode.OK,"上传成功",path);
    }

    @DeleteMapping("delete")
    public Result delete(String path){
        StorePath storePath = StorePath.praseFromUrl(path);
        fastFileStorageClient.deleteFile(storePath.getGroup(),storePath.getPath());
        return new Result(true,StatusCode.OK,"成功");
    }

}
