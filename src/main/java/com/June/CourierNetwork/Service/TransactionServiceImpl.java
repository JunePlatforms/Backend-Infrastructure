package com.June.CourierNetwork.Service;

import com.June.CourierNetwork.DTO.TransactionDetailsDTO;
import com.June.CourierNetwork.DTO.TransactionRequestDTO;
import com.June.CourierNetwork.Enum.PaymentType;
import com.June.CourierNetwork.Model.DeliveryDetails;
import com.June.CourierNetwork.Model.TransactionDetails;
import com.June.CourierNetwork.Model.TransactionDetailsRequest;
import com.June.CourierNetwork.POJO.PartialProduct;
import com.June.CourierNetwork.Repo.Contract.CustomerRepository;
import com.June.CourierNetwork.Repo.Contract.ProductRepository;
import com.June.CourierNetwork.Repo.Contract.TransactionRepository;
import com.June.CourierNetwork.Repo.Contract.UserRepository;
import com.June.CourierNetwork.Service.Contract.FileUploadService;
import com.June.CourierNetwork.Service.Contract.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final TransactionRepository transactionRepository;
    private final FileUploadService fileUploadService;
    @Value("${profile.image.upload.dir}")
    private String profileImageUploadDirectory;


    @Override
    public void save(TransactionRequestDTO transactionRequestDTO) {
        BigDecimal deliveryFee = BigDecimal.ZERO;
        TransactionDetailsRequest transactionDetailsRequest = new TransactionDetailsRequest();


        val totalSpent = calculateTotalSpent(transactionRequestDTO.getProductIds(), deliveryFee);

        transactionDetailsRequest.setTotalSpent(totalSpent.intValue());
        transactionDetailsRequest.setPaymentType(transactionRequestDTO.getPaymentType());
        transactionDetailsRequest.setCustomerId(transactionRequestDTO.getCustomerId());
        transactionDetailsRequest.setCourierId(transactionRequestDTO.getCourierId());
        transactionDetailsRequest.setCreatedOn(LocalDateTime.now());
        transactionDetailsRequest.setWasDelivered(transactionRequestDTO.getWasDelivered());

        val transactionId = transactionRepository.save(transactionDetailsRequest);

        transactionToProductMatching(transactionId, transactionRequestDTO.getProductIds());
    }

    @Override
    public void transactionHelper(DeliveryDetails deliveryDetails, PaymentType paymentType) {
        List<PartialProduct> packageDescription = deliveryDetails.getPackageDescription();
        List<Long> productIds = new ArrayList<>();

        for (PartialProduct product : packageDescription) {
            productIds.add(Long.valueOf(product.getProductId()));
        }

        TransactionRequestDTO transactionRequestDTO = TransactionRequestDTO.builder()
                .courierId(deliveryDetails.getCourierId())
                .productIds(productIds)
                .paymentType(paymentType)
                .customerId(deliveryDetails.getCustomerId())
                .wasDelivered(true)
                .build();

        save(transactionRequestDTO);
    }

    @Override
    public TransactionDetailsDTO getTransactionDetailsByCustomerNumber(String customerNumber) {
        val transactionDetails = transactionRepository.getTransactionDetailsByCustomerNumber(customerNumber);

        return TransactionDetailsDTO.builder()
                .customerFirstName(transactionDetails.getCustomerFirstName())
                .customerLastName(transactionDetails.getCustomerLastName())
                .customerNumber(transactionDetails.getCustomerNumber())
                .totalSpent(transactionDetails.getTotalSpent())
                .createdOn(transactionDetails.getCreatedOn())
                .wasDelivered(transactionDetails.getWasDelivered())
                .products(transactionDetails.getProducts())
                .paymentType(transactionDetails.getPaymentType())
                .customerId(transactionDetails.getCustomerId())
                .build();
    }

    @Override
    public List<TransactionDetailsDTO> getAllTransactionDetails() {
         val transactionDetails = transactionRepository.getAllTransactionDetails();

         List<TransactionDetailsDTO> transactionDetailsDTOList = new ArrayList<>();

         for (TransactionDetails transactionDetail : transactionDetails) {
             transactionDetailsDTOList.add(TransactionDetailsDTO.builder()
                     .customerFirstName(transactionDetail.getCustomerFirstName())
                     .customerLastName(transactionDetail.getCustomerLastName())
                     .customerNumber(transactionDetail.getCustomerNumber())
                     .totalSpent(transactionDetail.getTotalSpent())
                     .createdOn(transactionDetail.getCreatedOn())
                     .wasDelivered(transactionDetail.getWasDelivered())
                     .products(transactionDetail.getProducts())
                     .paymentType(transactionDetail.getPaymentType())
                     .customerId(transactionDetail.getCustomerId())
                     .build());
         }
         return transactionDetailsDTOList;
    }

    private void transactionToProductMatching(Long transactionId, List<Long> productIds) {
        transactionRepository.matchProductsToTransaction(transactionId, productIds);
    }

    private BigDecimal calculateTotalSpent(List<Long> productIds, BigDecimal deliveryFee) {
        return productRepository.calculateTotalSpent(productIds).add(deliveryFee);
    }
}
