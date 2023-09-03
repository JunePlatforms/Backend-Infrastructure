package com.June.CourierNetwork.Service.Contract;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileUploadService {
    void uploadFile(MultipartFile file, Long userId) throws IOException;

    byte[] getFile( String fileName) throws IOException;
}
