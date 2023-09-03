package com.June.CourierNetwork.Controller;

import com.June.CourierNetwork.Repo.Contract.UserRepository;
import com.June.CourierNetwork.Service.Contract.FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/customer")
public class CustomerUserController {

    private final UserRepository repository;
    private final FileUploadService fileUploadService;


    @PutMapping("/{id}/update/profileImage")
    public ResponseEntity<String> updateProfileImage(@PathVariable Long id, MultipartFile file) {
        val userOptional = repository.findUserById(id);
        if (userOptional.isPresent()) {
            try {
                fileUploadService.uploadFile(file, id);
                return ResponseEntity.ok("File uploaded successfully");
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file: " + e.getMessage());
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }


}
