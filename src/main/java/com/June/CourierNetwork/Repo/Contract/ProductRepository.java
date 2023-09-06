package com.June.CourierNetwork.Repo.Contract;

import com.June.CourierNetwork.Enum.PackageStatus;
import com.June.CourierNetwork.Enum.ShipmentType;
import com.June.CourierNetwork.Model.ProductDetails;
import com.June.CourierNetwork.Model.ProductDetailsRequest;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    void createProduct(ProductDetailsRequest productDetailsRequest);

    void updateProduct(Long productId, ProductDetailsRequest productDetailsRequest);

    void deleteProduct(Long productId);

    List<ProductDetails> findProductsByUserId(Long userId);

    List<ProductDetails> getAllProducts();

    Optional<ProductDetails> findProductById(Long packageId);

    void setShipmentType(Long packageId, ShipmentType shipmentType);

    void updateProductStatus(Long productId, PackageStatus status);
}
