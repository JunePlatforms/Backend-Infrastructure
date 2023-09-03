package com.June.CourierNetwork.Service.Contract;

import com.June.CourierNetwork.Model.ProductDetails;
import com.June.CourierNetwork.Model.ProductDetailsRequest;
import com.June.CourierNetwork.Model.ShippingLabel;

import java.util.List;

public interface WarehouseClerkService {

    ShippingLabel generateShippingLabel(Long productId);
}
