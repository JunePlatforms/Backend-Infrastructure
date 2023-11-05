package com.June.CourierNetwork.Model;

import com.June.CourierNetwork.Enum.Role;
import com.June.CourierNetwork.Enum.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  private String firstname;
  private String lastname;
  private String email;
  private String password;
  private String phoneNumber;
  private Role role;

  private Integer assessmentScore;
  private Integer rating;
  private Boolean isAvailable;
  private Boolean acceptedTermsAndConditions;
  private String vehicleMake;
  private String vehicleModel;
  private String licensePlateNumber;
  private VehicleType vehicleType;
  private byte[] policeRecord;
  private byte[] driversLicense;

  private String username;
  private String customerNumber;

}
