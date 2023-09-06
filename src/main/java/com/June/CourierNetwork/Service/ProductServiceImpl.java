package com.June.CourierNetwork.Service;

import com.June.CourierNetwork.Enum.PackageStatus;
import com.June.CourierNetwork.Enum.ShipmentType;
import com.June.CourierNetwork.Model.ProductDetails;
import com.June.CourierNetwork.Model.ProductDetailsRequest;
import com.June.CourierNetwork.Repo.Contract.ProductRepository;
import com.June.CourierNetwork.Repo.Contract.UserRepository;
import com.June.CourierNetwork.Service.Contract.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<ProductDetails> findProductById(Long packageId) {
        return productRepository.findProductById(packageId);
    }

    @Override
    public void setShipmentType(Long packageId, ShipmentType shipmentType) {
        productRepository.setShipmentType(packageId, shipmentType);
    }

    @Override
    public void updateProductStatus(Long productId, PackageStatus status) {
        productRepository.updateProductStatus(productId, status);
    }
}
