package com.June.CourierNetwork.Service.Contract;

import com.June.CourierNetwork.DTO.CourierDTO;
import com.June.CourierNetwork.Enum.ApplicationStatus;
import com.June.CourierNetwork.Enum.VehicleType;
import com.June.CourierNetwork.Model.Courier;

import java.util.List;
import java.util.Optional;

public interface CourierService {
    int updateRate(Long id, Integer rating);

    int updateAvailability(Long id, Boolean status);

    Optional<CourierDTO> findUserById(Long id);

    List<Courier> getAllCourierAccountsByStatus(ApplicationStatus status);

    List<Courier> getAllCouriersByVehicleType(VehicleType vehicleType);

    void updateApplicationStatus(Long id, ApplicationStatus status);
}
