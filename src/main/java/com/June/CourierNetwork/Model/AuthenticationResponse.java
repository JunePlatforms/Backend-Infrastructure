package com.June.CourierNetwork.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

  private String accessToken;
  private String refreshToken;

  public AuthenticationResponse(String message) {
    this.accessToken = message;
    this.refreshToken = message;
  }
}
