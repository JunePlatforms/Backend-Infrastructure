package com.June.CourierNetwork.Service.Contract;

import com.June.CourierNetwork.DTO.TransactionDetailsDTO;
import com.June.CourierNetwork.DTO.TransactionRequestDTO;
import com.June.CourierNetwork.Enum.PaymentType;
import com.June.CourierNetwork.Model.DeliveryDetails;

import java.util.List;

public interface TransactionService {

    void save(TransactionRequestDTO transactionRequestDTO);
    void transactionHelper(DeliveryDetails deliveryDetails, PaymentType paymentType);

    TransactionDetailsDTO getTransactionDetailsByCustomerNumber(String customerNumber);

    List<TransactionDetailsDTO> getAllTransactionDetails();
}
