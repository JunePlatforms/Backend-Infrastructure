package com.June.CourierNetwork.Repo.Contract;

import com.June.CourierNetwork.Model.ProductDetails;
import com.June.CourierNetwork.Model.ProductDetailsRequest;
import com.June.CourierNetwork.Model.ShippingLabel;
import com.June.CourierNetwork.Model.WarehouseClerk;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    void createProduct(ProductDetailsRequest productDetailsRequest);

    void updateProduct(Long productId, ProductDetailsRequest productDetailsRequest);

    void deleteProduct(Long productId);

    List<ProductDetails> findProductsByUserId(Long userId);

    List<ProductDetails> getAllProducts();
}
