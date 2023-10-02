package com.June.CourierNetwork.Service;

import com.June.CourierNetwork.DTO.CourierDTO;
import com.June.CourierNetwork.Enum.ApplicationStatus;
import com.June.CourierNetwork.Enum.VehicleType;
import com.June.CourierNetwork.Model.Courier;
import com.June.CourierNetwork.Repo.Contract.CourierRepository;
import com.June.CourierNetwork.Repo.Contract.UserRepository;
import com.June.CourierNetwork.Service.Contract.CourierService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourierServiceImpl implements CourierService {
    private final UserRepository userRepository;
    private final CourierRepository courierRepository;
    private final FileUploadServiceImpl fileUploadService;

    @Value("${police.record.upload.dir}")
    private String policeRecordUploadDirectory;

    @Value("${drivers.license.upload.dir}")
    private String driversLicenseUploadDirectory;

    @Override
    public int updateRate(Long id, Integer rating) {
        return courierRepository.updateRate(id, rating);
    }

    @Override
    public int updateAvailability(Long id, Boolean status) {
        return courierRepository.updateAvailability(id, status);}

    @Override
    public Optional<CourierDTO> findUserById(Long id) {
        return courierRepository.findByUserId(id);
    }

    @Override
    public List<Courier> getAllCourierAccountsByStatus(ApplicationStatus status) {
         val couriersDTO = courierRepository.getAllCourierAccountsByStatus(status);
         List<Courier> couriers;
         if (status.equals(ApplicationStatus.PENDING)){
             couriers = pendingCouriers(couriersDTO);
         }
         else if (status.equals(ApplicationStatus.APPROVED)){
             couriers = approvedCouriers(couriersDTO);
         }
         else {
             couriers = deniedCouriers(couriersDTO);
         }
         return couriers;
    }

    private List<Courier> deniedCouriers(List<CourierDTO> couriersDTO) {
        List<Courier> couriers = new ArrayList<>();
        for (val courierDTO : couriersDTO) {
            val user = userRepository.findUserById(courierDTO.getUserId()).orElseThrow();
            byte[] policeRecord = fileUploadService.getFile(courierDTO.getPoliceRecord(), policeRecordUploadDirectory);
            byte[] driversLicense = fileUploadService.getFile(courierDTO.getDriversLicense(), driversLicenseUploadDirectory);

            Courier courier = Courier.builder()
                    .courierId(courierDTO.getCourierId())
                    .reason(courierDTO.getReason())
                    .assessmentScore(courierDTO.getAssessmentScore())
                    .policeRecord(policeRecord)
                    .driversLicense(driversLicense)
                    .vehicleMake(courierDTO.getVehicleMake())
                    .vehicleModel(courierDTO.getVehicleModel())
                    .licensePlateNumber(courierDTO.getLicensePlateNumber())
                    .vehicleType(courierDTO.getVehicleType())
                    .applicationStatus(courierDTO.getApplicationStatus())
                    .user(user)
                    .build();

            couriers.add(courier);
        }
        return couriers;
    }

    private List<Courier> approvedCouriers(List<CourierDTO> couriersDTO) {
        List<Courier> couriers = new ArrayList<>();
        for (val courierDTO : couriersDTO) {
            val user = userRepository.findUserById(courierDTO.getUserId()).orElseThrow();
            byte[] policeRecord = fileUploadService.getFile(courierDTO.getPoliceRecord(), policeRecordUploadDirectory);
            byte[] driversLicense = fileUploadService.getFile(courierDTO.getDriversLicense(), driversLicenseUploadDirectory);

            Courier courier = Courier.builder()
                    .courierId(courierDTO.getCourierId())
                    .policeRecord(policeRecord)
                    .driversLicense(driversLicense)
                    .vehicleMake(courierDTO.getVehicleMake())
                    .vehicleModel(courierDTO.getVehicleModel())
                    .licensePlateNumber(courierDTO.getLicensePlateNumber())
                    .vehicleType(courierDTO.getVehicleType())
                    .applicationStatus(courierDTO.getApplicationStatus())
                    .isAvailable(courierDTO.getIsAvailable())
                    .rating(courierDTO.getRating())
                    .assessmentScore(courierDTO.getAssessmentScore())
                    .user(user)
                    .build();

            couriers.add(courier);
        }
        return couriers;
    }

    private List<Courier> pendingCouriers(List<CourierDTO> couriersDTO) {
        List<Courier> couriers = new ArrayList<>();
        for (val courierDTO : couriersDTO) {
            val user = userRepository.findUserById(courierDTO.getUserId()).orElseThrow();
            byte[] policeRecord = fileUploadService.getFile(courierDTO.getPoliceRecord(), policeRecordUploadDirectory);
            byte[] driversLicense = fileUploadService.getFile(courierDTO.getDriversLicense(), driversLicenseUploadDirectory);

            Courier courier = Courier.builder()
                    .courierId(courierDTO.getCourierId())
                    .assessmentScore(courierDTO.getAssessmentScore())
                    .policeRecord(policeRecord)
                    .driversLicense(driversLicense)
                    .vehicleMake(courierDTO.getVehicleMake())
                    .vehicleModel(courierDTO.getVehicleModel())
                    .licensePlateNumber(courierDTO.getLicensePlateNumber())
                    .vehicleType(courierDTO.getVehicleType())
                    .applicationStatus(courierDTO.getApplicationStatus())
                    .user(user)
                    .build();

            couriers.add(courier);
        }
        return couriers;
    }

    @Override
    public List<Courier> getAllCouriersByVehicleType(VehicleType vehicleType) {
         val couriersDTO = courierRepository.getAllCouriersByVehicleType(vehicleType);

        List<Courier> couriers = new ArrayList<>();

        for (val courierDTO : couriersDTO) {
            val user = userRepository.findUserById(courierDTO.getUserId()).orElseThrow();
            byte[] policeRecord = fileUploadService.getFile(courierDTO.getPoliceRecord(), policeRecordUploadDirectory);
            byte[] driversLicense = fileUploadService.getFile(courierDTO.getDriversLicense(), driversLicenseUploadDirectory);

            Courier courier = Courier.builder()
                    .courierId(courierDTO.getCourierId())
                    .policeRecord(policeRecord)
                    .driversLicense(driversLicense)
                    .vehicleMake(courierDTO.getVehicleMake())
                    .vehicleModel(courierDTO.getVehicleModel())
                    .licensePlateNumber(courierDTO.getLicensePlateNumber())
                    .vehicleType(courierDTO.getVehicleType())
                    .applicationStatus(courierDTO.getApplicationStatus())
                    .isAvailable(courierDTO.getIsAvailable())
                    .rating(courierDTO.getRating())
                    .assessmentScore(courierDTO.getAssessmentScore())
                    .user(user)
                    .build();

            couriers.add(courier);
        }

        return couriers;
    }

    @Override
    public void updateApplicationStatus(Long id, ApplicationStatus status) {
        courierRepository.updateApplicationStatus(id, status);
    }
}
