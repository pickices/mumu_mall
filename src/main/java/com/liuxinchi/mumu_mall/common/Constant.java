package com.liuxinchi.mumu_mall.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Constant {

    public static final String SALT = "gsa'jS?D1d45eW.kp]";
    public static final String MUMU_MALL_USER = "MUMU_MALL_USER";

    public static String FILE_UPLOAD_DIR;

    @Value("${file.upload.dir}")
    public void setFileUploadDir(String fileUploadDir) {
        FILE_UPLOAD_DIR = fileUploadDir;
    }
}