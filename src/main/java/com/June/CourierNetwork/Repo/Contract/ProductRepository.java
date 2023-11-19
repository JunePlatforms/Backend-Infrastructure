package com.June.CourierNetwork.Repo.Contract;

import com.June.CourierNetwork.DTO.ProductDetailsDTO;
import com.June.CourierNetwork.Enum.PackageStatus;
import com.June.CourierNetwork.Enum.ShipmentType;
import com.June.CourierNetwork.Model.ProductDetailsRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Long createProduct(ProductDetailsRequest productDetailsRequest);

    void updateProduct(Long productId, ProductDetailsRequest productDetailsRequest);

    void deleteProduct(Long productId);

    List<ProductDetailsDTO> findProductsByUserId(Long userId);

    List<ProductDetailsDTO> getAllProducts();

    Optional<ProductDetailsDTO> findProductById(Long packageId);

    void setShipmentType(Long packageId, ShipmentType shipmentType);

    void updateProductStatus(Long productId, PackageStatus status);

    String getPreAlert(Long productId);

    void uploadPreAlert(String newFileName, Long productId);

    void addProductsToShipment(List<Long> productIds, Long shipmentId);

    List<ProductDetailsDTO> findProductDetailsByShipmentId(Long shipmentId);

    Long findProductOwnerIdByProductId(Long productId);

    BigDecimal calculateTotalSpent(List<Long> productIds);
}
