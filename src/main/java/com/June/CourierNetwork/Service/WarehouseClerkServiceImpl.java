package com.June.CourierNetwork.Service;

import com.June.CourierNetwork.Model.ProductDetails;
import com.June.CourierNetwork.Model.ProductDetailsRequest;
import com.June.CourierNetwork.Model.ShippingLabel;
import com.June.CourierNetwork.Repo.Contract.UserRepository;
import com.June.CourierNetwork.Repo.Contract.WarehouseClerkRepository;
import com.June.CourierNetwork.Service.Contract.WarehouseClerkService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseClerkServiceImpl implements WarehouseClerkService {
    private final UserRepository userRepository;
    private final WarehouseClerkRepository warehouseClerkRepository;
    @Value("${june.address}")
    private String juneAddress;


    @Override
    public ShippingLabel generateShippingLabel(Long productId) {
        val shippingLabel = warehouseClerkRepository.generateShippingLabel(productId);
        shippingLabel.setJuneAddress(juneAddress);

        return shippingLabel;
    }
}
