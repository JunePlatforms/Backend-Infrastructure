package com.June.CourierNetwork.Service;

import com.June.CourierNetwork.Repo.Contract.CourierRepository;
import com.June.CourierNetwork.Repo.Contract.CustomerRepository;
import com.June.CourierNetwork.Repo.Contract.UserRepository;
import com.June.CourierNetwork.Service.Contract.CustomerService;
import com.June.CourierNetwork.Service.Contract.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    @Value("${file.upload.dir}")
    private String uploadDirectory;

    @Override
    public void uploadFile(MultipartFile file, Long userId) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path uploadPath = Paths.get(uploadDirectory);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = file.getInputStream()) {
            LocalDateTime currentDateTime = LocalDateTime.now();


            String newFileName =  currentDateTime + fileName;
            Path filePath = uploadPath.resolve(newFileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            String oldFileName = customerRepository.getProfileImage(userId);

            if (oldFileName != null && !oldFileName.equals("default.png")) {
                Path oldFilePath = uploadPath.resolve(oldFileName);
                if (Files.exists(oldFilePath)){
                    Files.delete(oldFilePath);
                }
            }
            customerRepository.updateProfileImage(userId, newFileName);
        }

    }

    @Override
    public byte[] getFile(String fileName) throws IOException {
        try {
            Path imagePath = Paths.get(uploadDirectory, fileName);
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
}
