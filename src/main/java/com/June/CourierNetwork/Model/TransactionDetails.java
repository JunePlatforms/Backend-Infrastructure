package com.June.CourierNetwork.Model;

import com.June.CourierNetwork.Enum.PaymentType;
import com.June.CourierNetwork.POJO.PartialProduct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDetails {

  private List<PartialProduct> products;
  private PaymentType paymentType;
  private Long customerId;
  private String customerFirstName;
  private String customerLastName;
  private String customerNumber;
  private Boolean wasDelivered;
  private BigDecimal totalSpent;
  private LocalDateTime createdOn;

}