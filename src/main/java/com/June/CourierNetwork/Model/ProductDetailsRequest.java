package com.June.CourierNetwork.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailsRequest {

  private String supplierName;
  private String weight;
  private String description;
  private String customerEmail;
  private String trackingNumber;
  private Long userId;

}
