package com.June.CourierNetwork.Service.Contract;

import com.June.CourierNetwork.DTO.DeliveryDetailsDTO;
import com.June.CourierNetwork.DTO.DeliveryDetailsRequestDTO;
import com.June.CourierNetwork.Enum.DeliveryStatus;

import java.util.List;

public interface DeliveryDetailsService {

    void saveDeliveryDetails(DeliveryDetailsRequestDTO deliveryDetailsRequestDTO);

    List<DeliveryDetailsDTO> getAllDeliveryDetails(DeliveryStatus status);

    DeliveryDetailsDTO getDeliveryDetailsByPackageId(Long packageId);

    List<DeliveryDetailsDTO> getAllDeliveryDetailsByCustomerId(Long customerId);

    List<DeliveryDetailsDTO> getAllDeliveryDetailsByCourierId(Long courierId);

    void assignCourier(Long deliveryId, Long courierId);

    void updateDeliveryStatus(Long deliveryId, DeliveryStatus status);

    void updateDeliveryDetails(Long deliveryId, DeliveryDetailsRequestDTO deliveryDetailsRequestDTO);
}
