package com.June.CourierNetwork.Repo.Contract;

import com.June.CourierNetwork.Enum.DeliveryStatus;
import com.June.CourierNetwork.Model.DeliveryDetails;
import com.June.CourierNetwork.Model.DeliveryDetailsRequest;

import java.util.List;
import java.util.Optional;

public interface DeliveryDetailsRepository {


    void save(DeliveryDetailsRequest deliveryDetails);

    Optional<List<DeliveryDetails>> getAllDeliveryDetails(DeliveryStatus status);

    Optional<DeliveryDetails> getDeliveryDetailsByPackageId(Long packageId);

    Optional<List<DeliveryDetails>> getAllDeliveryDetailsByCustomerId(Long customerId);

    Optional<List<DeliveryDetails>> getAllDeliveryDetailsByCourierId(Long courierId);

    void assignCourier(Long deliveryId, Long courierId);

    void updateDeliveryStatus(Long deliveryId, DeliveryStatus status);

    void updateDeliveryDetails(Long deliveryId, DeliveryDetailsRequest deliveryDetailsRequest);
}
