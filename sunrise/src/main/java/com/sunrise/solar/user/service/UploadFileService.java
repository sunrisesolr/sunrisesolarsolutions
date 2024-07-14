package com.sunrise.solar.user.service;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadFileService {
    public void uploadFile(String name, MultipartFile file) throws IOException, InvalidFormatException, org.apache.poi.openxml4j.exceptions.InvalidFormatException;
}
