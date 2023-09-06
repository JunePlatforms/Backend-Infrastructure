package com.June.CourierNetwork.Service.Contract;

import com.June.CourierNetwork.Enum.PackageStatus;
import com.June.CourierNetwork.Enum.ShipmentType;
import com.June.CourierNetwork.Model.ProductDetails;
import com.June.CourierNetwork.Model.ProductDetailsRequest;
import com.June.CourierNetwork.Model.ShippingLabel;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    void saveProductDetails(ProductDetailsRequest productDetailsRequest);

    void updateProductDetails(Long productId, ProductDetailsRequest productDetailsRequest);

    void deleteProductDetails(Long productId);

    List<ProductDetails> findProductsByEmail(String email);

    List<ProductDetails> getAllProducts();

    Optional<ProductDetails> findProductById(Long packageId);

    void setShipmentType(Long packageId, ShipmentType shipmentType);

    void updateProductStatus(Long productId, PackageStatus status);
}
