package com.June.CourierNetwork.Service;

import com.June.CourierNetwork.DTO.DeliveryDetailsDTO;
import com.June.CourierNetwork.DTO.DeliveryDetailsRequestDTO;
import com.June.CourierNetwork.DTO.ProductDetailsDTO;
import com.June.CourierNetwork.Enum.DeliveryStatus;
import com.June.CourierNetwork.Enum.PaymentType;
import com.June.CourierNetwork.Model.DeliveryDetails;
import com.June.CourierNetwork.Model.DeliveryDetailsRequest;
import com.June.CourierNetwork.Repo.Contract.DeliveryDetailsRepository;
import com.June.CourierNetwork.Repo.Contract.ProductRepository;
import com.June.CourierNetwork.Service.Contract.DeliveryDetailsService;
import com.June.CourierNetwork.Service.Contract.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryDetailsServiceImpl implements DeliveryDetailsService {

    private final DeliveryDetailsRepository deliveryDetailsRepository;
    private final ProductRepository productRepository;
    private final TransactionService transactionService;

    @Override
    public void saveDeliveryDetails(DeliveryDetailsRequestDTO deliveryDetailsRequestDTO) {
        val deliveryDetailsRequest = deliveryRequestBuilder(deliveryDetailsRequestDTO);

        try {
            List<ProductDetailsDTO> customerProducts = productRepository.findProductsByUserId
                    (deliveryDetailsRequestDTO.getCustomerId());

            long deliveryId = deliveryDetailsRepository.save(deliveryDetailsRequest);

            for (ProductDetailsDTO product : customerProducts) {
                deliveryDetailsRepository.addProduct(deliveryId, product.getId());
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public List<DeliveryDetailsDTO> getAllDeliveryDetails(DeliveryStatus status) {
        val deliveryDetailsList = deliveryDetailsRepository.getAllDeliveryDetails(status);

        List<DeliveryDetailsDTO> deliveryDetailsDTO = new ArrayList<>();

        for (DeliveryDetails deliveryDetails : deliveryDetailsList.orElseThrow()) {
            deliveryDetailsDTO.add(DeliveryDetailsDTO.builder()
                    .pickUpLocation(deliveryDetails.getPickUpLocation())
                    .dropOffLocation(deliveryDetails.getDropOffLocation())
                    .specialInstructions(deliveryDetails.getSpecialInstructions())
                    .customerFirstName(deliveryDetails.getCustomerFirstName())
                    .customerLastName(deliveryDetails.getCustomerLastName())
                    .customerPhoneNumber(deliveryDetails.getCustomerPhoneNumber())
                    .courierFirstName(deliveryDetails.getCourierFirstName())
                    .courierLastName(deliveryDetails.getCourierLastName())
                    .courierPhoneNumber(deliveryDetails.getCourierPhoneNumber())
                    .status(deliveryDetails.getStatus())
                    .deliveryDateTime(deliveryDetails.getDeliveryDateTime().toString())
                    .packageDescription(deliveryDetails.getPackageDescription())
                    .courierId(deliveryDetails.getCourierId())
                    .customerId(deliveryDetails.getCustomerId())
                    .build());
        }
        return deliveryDetailsDTO;
    }

    @Override
    public DeliveryDetailsDTO getDeliveryDetailsByPackageId(Long packageId) {
        val deliveryDetails = deliveryDetailsRepository.getDeliveryDetailsByPackageId(packageId).orElseThrow();

        return DeliveryDetailsDTO.builder()
                .pickUpLocation(deliveryDetails.getPickUpLocation())
                .dropOffLocation(deliveryDetails.getDropOffLocation())
                .packageDescription(deliveryDetails.getPackageDescription())
                .specialInstructions(deliveryDetails.getSpecialInstructions())
                .customerFirstName(deliveryDetails.getCustomerFirstName())
                .customerLastName(deliveryDetails.getCustomerLastName())
                .customerPhoneNumber(deliveryDetails.getCustomerPhoneNumber())
                .courierFirstName(deliveryDetails.getCourierFirstName())
                .courierLastName(deliveryDetails.getCourierLastName())
                .courierPhoneNumber(deliveryDetails.getCourierPhoneNumber())
                .status(deliveryDetails.getStatus())
                .deliveryDateTime(deliveryDetails.getDeliveryDateTime().toString())
                .courierId(deliveryDetails.getCourierId())
                .customerId(deliveryDetails.getCustomerId())
                .build();
    }

    @Override
    public List<DeliveryDetailsDTO> getAllDeliveryDetailsByCustomerId(Long customerId) {
        val deliveryDetailsList = deliveryDetailsRepository.getAllDeliveryDetailsByCustomerId(customerId);

        List<DeliveryDetailsDTO> deliveryDetailsDTO = new ArrayList<>();

        for (DeliveryDetails deliveryDetails : deliveryDetailsList.orElseThrow()) {
            deliveryDetailsDTO.add(DeliveryDetailsDTO.builder()
                    .pickUpLocation(deliveryDetails.getPickUpLocation())
                    .dropOffLocation(deliveryDetails.getDropOffLocation())
                    .packageDescription(deliveryDetails.getPackageDescription())
                    .specialInstructions(deliveryDetails.getSpecialInstructions())
                    .customerFirstName(deliveryDetails.getCustomerFirstName())
                    .customerLastName(deliveryDetails.getCustomerLastName())
                    .customerPhoneNumber(deliveryDetails.getCustomerPhoneNumber())
                    .courierFirstName(deliveryDetails.getCourierFirstName())
                    .courierLastName(deliveryDetails.getCourierLastName())
                    .courierPhoneNumber(deliveryDetails.getCourierPhoneNumber())
                    .status(deliveryDetails.getStatus())
                    .deliveryDateTime(deliveryDetails.getDeliveryDateTime().toString())
                    .courierId(deliveryDetails.getCourierId())
                    .customerId(deliveryDetails.getCustomerId())
                    .build());
        }
        return deliveryDetailsDTO;
    }

    @Override
    public List<DeliveryDetailsDTO> getAllDeliveryDetailsByCourierId(Long courierId) {
        val deliveryDetailsList = deliveryDetailsRepository.getAllDeliveryDetailsByCourierId(courierId);

        List<DeliveryDetailsDTO> deliveryDetailsDTO = new ArrayList<>();

        for (DeliveryDetails deliveryDetails : deliveryDetailsList.orElseThrow()) {
            deliveryDetailsDTO.add(DeliveryDetailsDTO.builder()
                    .pickUpLocation(deliveryDetails.getPickUpLocation())
                    .dropOffLocation(deliveryDetails.getDropOffLocation())
                    .packageDescription(deliveryDetails.getPackageDescription())
                    .specialInstructions(deliveryDetails.getSpecialInstructions())
                    .customerFirstName(deliveryDetails.getCustomerFirstName())
                    .customerLastName(deliveryDetails.getCustomerLastName())
                    .customerPhoneNumber(deliveryDetails.getCustomerPhoneNumber())
                    .courierFirstName(deliveryDetails.getCourierFirstName())
                    .courierLastName(deliveryDetails.getCourierLastName())
                    .courierPhoneNumber(deliveryDetails.getCourierPhoneNumber())
                    .status(deliveryDetails.getStatus())
                    .deliveryDateTime(deliveryDetails.getDeliveryDateTime().toString())
                    .courierId(deliveryDetails.getCourierId())
                    .customerId(deliveryDetails.getCustomerId())
                    .build());
        }
        return deliveryDetailsDTO;
    }

    @Override
    public void assignCourier(Long deliveryId, Long courierId) {
        deliveryDetailsRepository.assignCourier(deliveryId, courierId);
    }

    @Override
    public void updateDeliveryStatus(Long deliveryId, DeliveryStatus status, PaymentType paymentType) {
        val deliveryDetails = deliveryDetailsRepository.updateDeliveryStatus(deliveryId, status);

        if (deliveryDetails != null) {
            transactionService.transactionHelper(deliveryDetails, paymentType);
        }
    }

    @Override
    public void updateDeliveryDetails(Long deliveryId, DeliveryDetailsRequestDTO deliveryDetailsRequestDTO) {
        val deliveryDetailsRequest = deliveryRequestBuilder(deliveryDetailsRequestDTO);

        deliveryDetailsRepository.updateDeliveryDetails(deliveryId, deliveryDetailsRequest);
    }

    private DeliveryDetailsRequest deliveryRequestBuilder(DeliveryDetailsRequestDTO deliveryDetailsRequestDTO) {
        LocalDateTime parsedDateTime = LocalDateTime.parse(deliveryDetailsRequestDTO.getDeliveryDateTime());

        return DeliveryDetailsRequest.builder()
                .pickUpLocation(deliveryDetailsRequestDTO.getPickUpLocation())
                .dropOffLocation(deliveryDetailsRequestDTO.getDropOffLocation())
                .specialInstructions(deliveryDetailsRequestDTO.getSpecialInstructions())
                .customerId(deliveryDetailsRequestDTO.getCustomerId())
                .deliveryDateTime(parsedDateTime)
                .build();
    }

}
