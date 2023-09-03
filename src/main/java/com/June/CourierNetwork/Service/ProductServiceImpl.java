package com.June.CourierNetwork.Service;

import com.June.CourierNetwork.Model.ProductDetails;
import com.June.CourierNetwork.Model.ProductDetailsRequest;
import com.June.CourierNetwork.Model.ShippingLabel;
import com.June.CourierNetwork.Repo.Contract.ProductRepository;
import com.June.CourierNetwork.Repo.Contract.UserRepository;
import com.June.CourierNetwork.Repo.Contract.WarehouseClerkRepository;
import com.June.CourierNetwork.Service.Contract.ProductService;
import com.June.CourierNetwork.Service.Contract.WarehouseClerkService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;


    @Override
    public void saveProductDetails(ProductDetailsRequest productDetailsRequest) {
        val userId = userRepository.findUserByEmail(productDetailsRequest.getCustomerEmail()).get().getId();

        productDetailsRequest.setUserId(userId);

        productRepository.createProduct(productDetailsRequest);

    }

    @Override
    public void updateProductDetails(Long productId, ProductDetailsRequest productDetailsRequest) {
        productRepository.updateProduct(productId, productDetailsRequest);

    }

    @Override
    public void deleteProductDetails(Long productId) {
        productRepository.deleteProduct(productId);

    }

    @Override
    public List<ProductDetails> findProductsByEmail(String email) {
        val userId = userRepository.findUserByEmail(email).get().getId();

        return productRepository.findProductsByUserId(userId);}

    @Override
    public List<ProductDetails> getAllProducts() {
        return productRepository.getAllProducts();
    }
}
