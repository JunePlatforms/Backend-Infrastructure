package com.June.CourierNetwork.Repo.Contract;

import com.June.CourierNetwork.Model.TransactionDetails;
import com.June.CourierNetwork.Model.TransactionDetailsRequest;

import java.util.List;

public interface TransactionRepository {
    Long save(TransactionDetailsRequest transactionDetailsRequest);

    void matchProductsToTransaction(Long transactionId, List<Long> productIds);

    TransactionDetails getTransactionDetailsByCustomerNumber(String customerNumber);

    List<TransactionDetails> getAllTransactionDetails();
}
