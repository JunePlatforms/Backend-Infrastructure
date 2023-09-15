package com.June.CourierNetwork.Model;

import com.June.CourierNetwork.Enum.DeliveryStatus;
import com.June.CourierNetwork.POJO.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryDetails {

  private Address pickUpLocation;
  private Address dropOffLocation;
  private String packageDescription;
  private LocalDateTime deliveryDateTime;
  private String specialInstructions;
  private String customerFirstName;
  private String customerLastName;
  private String customerPhoneNumber;
  private DeliveryStatus status;
  private String courierFirstName;
  private String courierLastName;
  private String courierPhoneNumber;

}
