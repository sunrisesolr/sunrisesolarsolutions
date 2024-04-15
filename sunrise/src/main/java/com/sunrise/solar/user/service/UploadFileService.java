package com.sunrise.solar.user.service;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadFileService {

    public void uploadFile(String name, MultipartFile file) throws IOException, InvalidFormatException;
}
