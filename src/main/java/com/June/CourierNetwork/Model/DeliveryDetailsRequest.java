package com.June.CourierNetwork.Model;

import com.June.CourierNetwork.POJO.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryDetailsRequest {

  private Address pickUpLocation;
  private Address dropOffLocation;
  private String specialInstructions;
  private LocalDateTime deliveryDateTime;
  private Long customerId;
  private Long packageId;

}
