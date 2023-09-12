package com.June.CourierNetwork.Service.Contract;

import com.June.CourierNetwork.Enum.PackageStatus;
import com.June.CourierNetwork.Enum.ShipmentType;
import com.June.CourierNetwork.Model.DeliveryDetailsRequest;
import com.June.CourierNetwork.Model.ProductDetails;
import com.June.CourierNetwork.Model.ProductDetailsRequest;

import java.io.IOException;
import java.util.List;

public interface DeliveryDetailsService {

    void saveDeliveryDetails(DeliveryDetailsRequest deliveryDetailsRequest);

    List<DeliveryDetailsRequest> getAllDeliveryDetails();
}
