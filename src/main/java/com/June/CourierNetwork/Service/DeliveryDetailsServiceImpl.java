package com.June.CourierNetwork.Service;

import com.June.CourierNetwork.DTO.DeliveryDetailsDTO;
import com.June.CourierNetwork.DTO.DeliveryDetailsRequestDTO;
import com.June.CourierNetwork.Enum.DeliveryStatus;
import com.June.CourierNetwork.Model.DeliveryDetails;
import com.June.CourierNetwork.Model.DeliveryDetailsRequest;
import com.June.CourierNetwork.Repo.Contract.DeliveryDetailsRepository;
import com.June.CourierNetwork.Service.Contract.DeliveryDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryDetailsServiceImpl implements DeliveryDetailsService {

    private final DeliveryDetailsRepository deliveryDetailsRepository;

    @Override
    public void saveDeliveryDetails(DeliveryDetailsRequestDTO deliveryDetailsRequestDTO) {
        val deliveryDetailsRequest = deliveryRequestBuilder(deliveryDetailsRequestDTO);

        deliveryDetailsRepository.save(deliveryDetailsRequest);
    }

    @Override
    public List<DeliveryDetailsDTO> getAllDeliveryDetails(DeliveryStatus status) {
        val deliveryDetailsList = deliveryDetailsRepository.getAllDeliveryDetails(status);

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
                    .build());
        }
        return deliveryDetailsDTO;
    }

    @Override
    public void assignCourier(Long deliveryId, Long courierId) {
        deliveryDetailsRepository.assignCourier(deliveryId, courierId);
    }

    @Override
    public void updateDeliveryStatus(Long deliveryId, DeliveryStatus status) {
        deliveryDetailsRepository.updateDeliveryStatus(deliveryId, status);
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
                .packageId(deliveryDetailsRequestDTO.getPackageId())
                .deliveryDateTime(parsedDateTime)
                .build();
    }

}
