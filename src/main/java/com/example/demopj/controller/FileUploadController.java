package com.example.demopj.controller;

import com.example.demopj.Utils.AliOssUtil;
import com.example.demopj.type.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/upload")
public class FileUploadController {

    @PostMapping
    public Result<String> upload(MultipartFile file) throws IOException{
//        把文件内容存储到本地磁盘
        String originFilename = file.getOriginalFilename();
//        保证文件名字是唯一的，从而防止文件覆盖
        String filename = UUID.randomUUID().toString() + originFilename.substring(originFilename.lastIndexOf("."));
        String url = AliOssUtil.uploadFile(filename, file.getInputStream());
        return Result.success(url);
    }

}
