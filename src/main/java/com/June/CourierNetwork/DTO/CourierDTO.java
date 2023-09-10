package com.June.CourierNetwork.DTO;

import com.June.CourierNetwork.Enum.ApplicationStatus;
import com.June.CourierNetwork.Enum.VehicleType;
import com.June.CourierNetwork.Model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourierDTO {
    Long courierId;
    Integer assessmentScore;
    Integer rating;
    Boolean isAvailable;
    Boolean acceptedTermsAndConditions;
    String policeRecord;
    String driversLicense;
    String vehicleMake;
    String vehicleModel;
    String licensePlateNumber;
    String reason;
    VehicleType vehicleType;
    ApplicationStatus applicationStatus;
    Long userId;

    User user;

}
