package com.mmall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by sun on 2019/10/15.
 */
public interface IFileService {

    String upload(MultipartFile file, String path);

}
