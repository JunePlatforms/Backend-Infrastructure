package com.June.CourierNetwork.Service;

import com.June.CourierNetwork.Model.ProductDetails;
import com.June.CourierNetwork.Model.ProductDetailsRequest;
import com.June.CourierNetwork.Repo.Contract.UserRepository;
import com.June.CourierNetwork.Repo.Contract.WarehouseClerkRepository;
import com.June.CourierNetwork.Service.Contract.WarehouseClerkService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseClerkServiceImpl implements WarehouseClerkService {
    private final UserRepository userRepository;
    private final WarehouseClerkRepository warehouseClerkRepository;


    @Override
    public void saveProductDetails(ProductDetailsRequest productDetailsRequest) {
        val userId = userRepository.findUserByEmail(productDetailsRequest.getCustomerEmail()).get().getId();

        productDetailsRequest.setUserId(userId);

        warehouseClerkRepository.createProduct(productDetailsRequest);

    }

    @Override
    public void updateProductDetails(Long productId, ProductDetailsRequest productDetailsRequest) {
        warehouseClerkRepository.updateProduct(productId, productDetailsRequest);

    }

    @Override
    public void deleteProductDetails(Long productId) {
        warehouseClerkRepository.deleteProduct(productId);

    }

    @Override
    public List<ProductDetails> findProductsByEmail(String email) {
        val userId = userRepository.findUserByEmail(email).get().getId();

        return warehouseClerkRepository.findProductsByUserId(userId);}

    @Override
    public List<ProductDetails> getAllProducts() {
        return warehouseClerkRepository.getAllProducts();
    }
}
