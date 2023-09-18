package com.June.CourierNetwork.Model;

import com.June.CourierNetwork.Enum.DeliveryStatus;
import com.June.CourierNetwork.POJO.Address;
import com.June.CourierNetwork.POJO.PartialProduct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryDetails {

  private Address pickUpLocation;
  private Address dropOffLocation;
  private List<PartialProduct> packageDescription;
  private LocalDateTime deliveryDateTime;
  private String specialInstructions;
  private Long customerId;
  private String customerFirstName;
  private String customerLastName;
  private String customerPhoneNumber;
  private DeliveryStatus status;
  private Long courierId;
  private String courierFirstName;
  private String courierLastName;
  private String courierPhoneNumber;

}
