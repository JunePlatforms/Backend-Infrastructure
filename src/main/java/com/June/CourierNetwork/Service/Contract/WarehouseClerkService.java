package com.June.CourierNetwork.Service.Contract;

import com.June.CourierNetwork.Model.ProductDetails;
import com.June.CourierNetwork.Model.ProductDetailsRequest;

import java.util.List;

public interface WarehouseClerkService {
    void saveProductDetails(ProductDetailsRequest productDetailsRequest);

    void updateProductDetails(Long productId, ProductDetailsRequest productDetailsRequest);

    void deleteProductDetails(Long productId);

    List<ProductDetails> findProductsByEmail(String email);

    List<ProductDetails> getAllProducts();
}
