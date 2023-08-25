package com.June.CourierNetwork.Model;

import com.June.CourierNetwork.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

}
