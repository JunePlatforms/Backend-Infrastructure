package com.June.CourierNetwork.Repo.Contract;

import com.June.CourierNetwork.DTO.ProductDetailsDTO;
import com.June.CourierNetwork.Enum.PackageStatus;
import com.June.CourierNetwork.Enum.ShipmentType;
import com.June.CourierNetwork.Model.DeliveryDetailsRequest;
import com.June.CourierNetwork.Model.ProductDetailsRequest;

import java.util.List;
import java.util.Optional;

public interface DeliveryDetailsRepository {


    void save(DeliveryDetailsRequest deliveryDetailsRequest);

    Optional<List<DeliveryDetailsRequest>> getAllDeliveryDetails();
}
