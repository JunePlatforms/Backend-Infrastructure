package com.June.CourierNetwork.Service;

import com.June.CourierNetwork.Repo.Contract.*;
import com.June.CourierNetwork.Service.Contract.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.text.Normalizer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {
    private final CustomerRepository customerRepository;
    private final ShipmentRepository shipmentRepository;
    private final CourierRepository courierRepository;
    private final ProductRepository productRepository;




    @Override
    public void uploadProfileImage(MultipartFile file, Long userId, String filePath) throws IOException {
        String oldFileName = customerRepository.getProfileImage(userId);

        try {
            String newFileName = uploadFile(file, filePath, oldFileName);
            customerRepository.updateProfileImage(userId, newFileName);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void uploadAirWayInvoice(MultipartFile file, Long shipmentId, String filePath) throws IOException {
        String oldFileName = shipmentRepository.getAirwayInvoiceFileName(shipmentId);
        try {
            String newFileName = uploadFile(file, filePath, oldFileName);

            shipmentRepository.updateAirwayInvoiceFileName(newFileName, shipmentId);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void uploadShipmentManifest(MultipartFile file, Long shipmentId, String filePath) throws IOException {
        String oldFileName = shipmentRepository.getAirwayInvoiceFileName(shipmentId);
        try {
            String newFileName = uploadFile(file, filePath, oldFileName);

            shipmentRepository.updateShipmentManifestFileName(newFileName, shipmentId);
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public byte[] getFile(String fileName, String filePath){
        if (fileName == null) {
            return new byte[0];
        }

        try {
            Path imagePath = Paths.get(filePath, fileName);
            Resource imageResource = new FileSystemResource(imagePath);

            if (imageResource.exists()) {
                return imageResource.getInputStream().readAllBytes();
            }else {
                return new byte[0];
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    @Override
    public void uploadPoliceRecord(MultipartFile file, Long userId, String filePath) throws IOException {
        String oldFileName = courierRepository.getPoliceRecord(userId);
        try {
            String newFileName = uploadFile(file, filePath, oldFileName);

            courierRepository.updatePoliceRecord(newFileName, userId);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void uploadDriversLicense(MultipartFile file, Long userId, String filePath) throws IOException {
        String oldFileName = courierRepository.getDriversLicense(userId);
        try {
            String newFileName = uploadFile(file, filePath, oldFileName);

            courierRepository.updateDriversLicense(newFileName, userId);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void uploadPreAlert(MultipartFile file, Long productId, String filePath) {
        String oldFileName = productRepository.getPreAlert(productId);
        try {
            String newFileName = uploadFile(file, filePath, oldFileName);

            productRepository.uploadPreAlert(newFileName, productId);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String uploadFile(MultipartFile file, String uploadDir, String oldFileName) throws IOException {

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path uploadPath = Paths.get(uploadDir);
        String newFileName;

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String sanitizedFileName;
        try (InputStream inputStream = file.getInputStream()) {
            LocalDateTime currentDateTime = LocalDateTime.now();


            newFileName = currentDateTime + fileName;
            sanitizedFileName = sanitizeFileName(newFileName);

            Path filePath = uploadPath.resolve(sanitizedFileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

            if (oldFileName != null) {
                Path oldFilePath = uploadPath.resolve(oldFileName);
                if (Files.exists(oldFilePath)) {
                    Files.delete(oldFilePath);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }

        return sanitizedFileName;
    }

    public static String sanitizeFileName(String fileName) {
        // Remove special characters
        String sanitizedFileName = fileName.replaceAll("[\\\\/:\"*?<>|]", "_");

        // Normalize the file name (remove diacritics)
        sanitizedFileName = Normalizer.normalize(sanitizedFileName, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");

        // Add a timestamp to ensure uniqueness
//        LocalDateTime currentDateTime = LocalDateTime.now();
//        String timestamp = currentDateTime.toString().replace(":", "_").replace(".", "_");
//        sanitizedFileName = timestamp + "_" + sanitizedFileName;

        return sanitizedFileName;
    }
}
