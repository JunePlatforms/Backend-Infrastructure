package com.June.CourierNetwork.DTO;

import com.June.CourierNetwork.POJO.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryDetailsRequestDTO {

  private Address pickUpLocation;
  private Address dropOffLocation;
  private String packageDescription;
  private String specialInstructions;
  private Long customerId;
  private Long packageId;

}
