package com.June.CourierNetwork.DTO;

import com.June.CourierNetwork.Enum.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequestDTO {
  private Long courierId;
  private List<Long> productIds;
  private PaymentType paymentType;
  private Long customerId;
  private Boolean wasDelivered;
}

