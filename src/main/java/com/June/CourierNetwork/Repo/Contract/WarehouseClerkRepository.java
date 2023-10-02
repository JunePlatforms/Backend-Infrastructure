package com.June.CourierNetwork.Repo.Contract;

import com.June.CourierNetwork.Model.ProductDetails;
import com.June.CourierNetwork.Model.ProductDetailsRequest;
import com.June.CourierNetwork.Model.ShippingLabel;
import com.June.CourierNetwork.Model.WarehouseClerk;

import java.util.List;
import java.util.Optional;

public interface WarehouseClerkRepository {

    Optional<WarehouseClerk> findByUserId(long userId);

    Long save(WarehouseClerk warehouseClerk);

    ShippingLabel generateShippingLabel(Long productId);
}
