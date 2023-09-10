package com.June.CourierNetwork.Model;

import com.June.CourierNetwork.Enum.ApplicationStatus;
import com.June.CourierNetwork.Enum.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Courier{
    Long courierId;
    Integer assessmentScore;
    Integer rating;
    Boolean isAvailable;
    Boolean acceptedTermsAndConditions;
    byte[] policeRecord;
    byte[] driversLicense;
    String vehicleMake;
    String vehicleModel;
    String licensePlateNumber;
    String reason;
    VehicleType vehicleType;
    ApplicationStatus applicationStatus;


    User user;

}
