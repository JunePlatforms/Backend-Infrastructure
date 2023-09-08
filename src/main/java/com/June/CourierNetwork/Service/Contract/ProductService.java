package com.June.CourierNetwork.Service.Contract;

import com.June.CourierNetwork.Enum.PackageStatus;
import com.June.CourierNetwork.Enum.ShipmentType;
import com.June.CourierNetwork.Model.ProductDetails;
import com.June.CourierNetwork.Model.ProductDetailsRequest;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    void saveProductDetails(ProductDetailsRequest productDetailsRequest);

    void updateProductDetails(Long productId, ProductDetailsRequest productDetailsRequest);

    void deleteProductDetails(Long productId);

    List<ProductDetails> findProductsByEmail(String email) throws IOException;

    List<ProductDetails> getAllProducts() throws IOException;

    ProductDetails findProductById(Long packageId) throws IOException;

    void setShipmentType(Long packageId, ShipmentType shipmentType);

    void updateProductStatus(Long productId, PackageStatus status);

    void addProductsToShipment(List<Long> productIds, Long shipmentId);

    List<ProductDetails> findProductDetailsByShipmentId(Long shipmentId) throws IOException;
}
