package com.June.CourierNetwork.Service.Contract;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileUploadService {
    void uploadProfileImage(MultipartFile file, Long userId, String filePath) throws IOException;
    void uploadAirWayInvoice(MultipartFile file, Long shipmentId, String filePath) throws IOException;
    void uploadShipmentManifest(MultipartFile file, Long shipmentId, String filePath) throws IOException;

    byte[] getFile(String fileName, String filePath) throws IOException;
}
