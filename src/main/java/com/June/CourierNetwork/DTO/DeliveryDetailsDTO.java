package com.June.CourierNetwork.DTO;

import com.June.CourierNetwork.Enum.DeliveryStatus;
import com.June.CourierNetwork.POJO.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryDetailsDTO {

  private Address pickUpLocation;
  private Address dropOffLocation;
  private String packageDescription;
  private String specialInstructions;
  private String customerFirstName;
  private String customerLastName;
  private String customerPhoneNumber;
  private DeliveryStatus status;
  private String courierFirstName;
  private String courierLastName;
  private String courierPhoneNumber;

}
