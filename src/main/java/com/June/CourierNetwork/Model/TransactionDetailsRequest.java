package com.June.CourierNetwork.Model;

import com.June.CourierNetwork.Enum.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDetailsRequest {

  private Long courierId;
  private Long customerId;
  private Boolean wasDelivered;
  private LocalDateTime createdOn;
  private int totalSpent;
  private PaymentType paymentType;

}
