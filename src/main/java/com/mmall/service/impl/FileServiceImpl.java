package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.mmall.service.IFileService;
import com.mmall.util.FTPUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by sun on 2019/10/15.
 */

@Slf4j
@Service("iFileService")
public class FileServiceImpl implements IFileService{

    public String upload(MultipartFile file, String path){
        String fileName = file.getOriginalFilename();
        //拿到扩展名 +1是为了去掉“.”
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".")+1);
        //新的上传文件名
        String uploadFileName = UUID.randomUUID().toString()+"."+fileExtensionName;
        log.info("开始上传文件，上传文件的文件名:{},上传的路径:{},新文件名:{}",fileName,path,uploadFileName);

        File fileDir =new File(path);
        if (!fileDir.exists()) {
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        File targetFile = new File(path,uploadFileName);

        try {
            file.transferTo(targetFile);
            //文件上传成功

            FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            //已经上传到ftp服务器上

            // 上传完之后，删除upload下面的文件
            targetFile.delete();

        } catch (IOException e) {
            log.error("上传文件异常",e);
            return null;
        }

        return targetFile.getName();
    }

}
