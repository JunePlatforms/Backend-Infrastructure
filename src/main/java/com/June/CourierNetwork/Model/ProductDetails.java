package com.June.CourierNetwork.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetails {

  private Long id;
  private String supplierName;
  private String weight;
  private String description;
  private String customerNumber;
  private String trackingNumber;


}
