package com.June.CourierNetwork.Controller;

import com.June.CourierNetwork.Enum.ApplicationStatus;
import com.June.CourierNetwork.Enum.VehicleType;
import com.June.CourierNetwork.Model.Courier;
import com.June.CourierNetwork.Repo.Contract.UserRepository;
import com.June.CourierNetwork.Service.Contract.CourierService;
import com.June.CourierNetwork.Service.Contract.FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/courier")
public class CourierUserController {

    private final UserRepository repository;
    private final CourierService courierService;
    private final FileUploadService fileUploadService;
    @Value("${police.record.upload.dir}")
    private String policeRecordUploadDirectory;

    @Value("${drivers.license.upload.dir}")
    private String driversLicenseUploadDirectory;

    @PutMapping("/{id}/update/rating")
    public ResponseEntity<String> updateCourierRating(@PathVariable Long id, Integer rating){
        val userOptional = repository.findUserById(id);
        if (userOptional.isPresent()){
            courierService.updateRate(id, rating);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}/update/availability")
    public ResponseEntity<String> updateCourierAvailability(@PathVariable Long id, Boolean status){
        val userOptional = repository.findUserById(id);
        if (userOptional.isPresent()){
            courierService.updateAvailability(id, status);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("{userId}/upload/police/record")
    public ResponseEntity<String> uploadPoliceRecord(@PathVariable Long userId, MultipartFile file) throws IOException {
        val userOptional = repository.findUserById(userId);
        if (userOptional.isPresent()) {
            try {
                fileUploadService.uploadPoliceRecord(file, userId, policeRecordUploadDirectory);
                return ResponseEntity.ok("File uploaded successfully");
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file: " + e.getMessage());
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    @PutMapping("{userId}/upload/drivers/license")
    public ResponseEntity<String> uploadDriversLicense(@PathVariable Long userId, MultipartFile file) throws IOException {
        val userOptional = repository.findUserById(userId);
        if (userOptional.isPresent()) {
            try {
                fileUploadService.uploadDriversLicense(file, userId, driversLicenseUploadDirectory);
                return ResponseEntity.ok("File uploaded successfully");
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file: " + e.getMessage());
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    @GetMapping("/{id}/get/police/record")
    public ResponseEntity<byte[]> getPoliceRecord(@PathVariable Long id){
        val userOptional = courierService.findUserById(id);
        if (userOptional.isPresent()) {
            try {
                return ResponseEntity.ok(fileUploadService.getFile(userOptional.get().getPoliceRecord(), policeRecordUploadDirectory));
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new byte[0]);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new byte[0]);
    }

    @GetMapping("/{id}/get/drivers/license")
    public ResponseEntity<byte[]> getDriversLicense(@PathVariable Long id){
        val userOptional = courierService.findUserById(id);
        if (userOptional.isPresent()) {
            try {
                return ResponseEntity.ok(fileUploadService.getFile(userOptional.get().getDriversLicense(), driversLicenseUploadDirectory));
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new byte[0]);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new byte[0]);
    }

    @GetMapping("/get/all/courier/accounts/by/status")
    public ResponseEntity<List<Courier>> getAllCourierAccountsByStatus(ApplicationStatus status){
        try {
            return ResponseEntity.ok(courierService.getAllCourierAccountsByStatus(status));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/get/all/couriers/by/vehicle/type")
    public ResponseEntity<List<Courier>> getAllCouriersByVehicleType(@RequestParam VehicleType vehicleType){
        try {
            return ResponseEntity.ok(courierService.getAllCouriersByVehicleType(vehicleType));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("{userId}/update/application/status")
    public ResponseEntity<String> updateApplicationStatus(@PathVariable Long userId, ApplicationStatus status){
        try {
            courierService.updateApplicationStatus(userId, status);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
