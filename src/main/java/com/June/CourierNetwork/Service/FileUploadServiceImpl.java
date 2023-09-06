package com.June.CourierNetwork.Service;

import com.June.CourierNetwork.Repo.Contract.CustomerRepository;
import com.June.CourierNetwork.Repo.Contract.ShipmentRepository;
import com.June.CourierNetwork.Repo.Contract.UserRepository;
import com.June.CourierNetwork.Service.Contract.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

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

    private String uploadFile(MultipartFile file, String uploadDir, String oldFileName) throws IOException  {

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path uploadPath = Paths.get(uploadDir);
        String newFileName;

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = file.getInputStream()) {
            LocalDateTime currentDateTime = LocalDateTime.now();


            newFileName =  currentDateTime + fileName;
            Path filePath = uploadPath.resolve(newFileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

            if (oldFileName != null) {
                Path oldFilePath = uploadPath.resolve(oldFileName);
                if (Files.exists(oldFilePath)){
                    Files.delete(oldFilePath);
                }
            }

        }catch (IOException e) {
        e.printStackTrace();

        return null;
        }

        return newFileName;}
}
