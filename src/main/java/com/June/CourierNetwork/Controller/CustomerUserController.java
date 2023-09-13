package com.June.CourierNetwork.Controller;

import com.June.CourierNetwork.Enum.ShipmentType;
import com.June.CourierNetwork.Repo.Contract.UserRepository;
import com.June.CourierNetwork.Service.Contract.FileUploadService;
import com.June.CourierNetwork.Service.Contract.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/customer")
public class CustomerUserController {

    private final UserRepository repository;
    private final FileUploadService fileUploadService;
    private final ProductService productService;
    @Value("${profile.image.upload.dir}")
    private String profileImageUploadDirectory;
    @Value("${pre.alert.upload.dir}")
    private String preAlertImageUploadDirectory;


    @PutMapping("/{id}/update/profileImage")
    public ResponseEntity<String> updateProfileImage(@PathVariable Long id, MultipartFile file) {
        val userOptional = repository.findUserById(id);
        if (userOptional.isPresent()) {
            try {
                fileUploadService.uploadProfileImage(file, id, profileImageUploadDirectory);
                return ResponseEntity.ok("File uploaded successfully");
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file: " + e.getMessage());
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    @PutMapping("/{productId}/set/shipmentType")
    public ResponseEntity<String> setShipmentType(@PathVariable Long productId, @RequestParam ShipmentType shipmentType) throws IOException {
        productService.findProductById(productId);
        productService.setShipmentType(productId, shipmentType);
        return ResponseEntity.ok("Successfully updated shipment type");
    }

    @PutMapping("/{productId}/upload/preAlert")
    public ResponseEntity<String> uploadPreAlert(@PathVariable Long productId, MultipartFile file) throws IOException {
        productService.findProductById(productId);
        fileUploadService.uploadPreAlert(file, productId, preAlertImageUploadDirectory);
        return ResponseEntity.ok("File uploaded successfully");
    }

}
