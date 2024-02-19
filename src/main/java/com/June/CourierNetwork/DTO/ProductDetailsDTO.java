package com.June.CourierNetwork.DTO;

import com.June.CourierNetwork.Enum.PackageStatus;
import com.June.CourierNetwork.Enum.ShipmentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailsDTO {

  private Long id;
  private String supplierName;
  private String weight;
  private String description;
  private String customerNumber;
  private String customerFirstName;
  private String customerLastName;
  private String trackingNumber;
  private ShipmentType shipmentType;
  private PackageStatus packageStatus;
  private String preAlertFileName;
  private Date createdOn;
  private String JunId;
  private Date updatedOn;


}
