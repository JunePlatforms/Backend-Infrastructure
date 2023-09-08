package com.June.CourierNetwork.Service;

import com.June.CourierNetwork.DTO.ProductDetailsDTO;
import com.June.CourierNetwork.Enum.PackageStatus;
import com.June.CourierNetwork.Enum.ShipmentType;
import com.June.CourierNetwork.Model.ProductDetails;
import com.June.CourierNetwork.Model.ProductDetailsRequest;
import com.June.CourierNetwork.Repo.Contract.ProductRepository;
import com.June.CourierNetwork.Repo.Contract.UserRepository;
import com.June.CourierNetwork.Service.Contract.FileUploadService;
import com.June.CourierNetwork.Service.Contract.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final FileUploadService fileUploadService;
    @Value("${pre.alert.upload.dir}")
    private String preAlertImageUploadDirectory;


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
    public List<ProductDetails> findProductsByEmail(String email) throws IOException {
        val userId = userRepository.findUserByEmail(email).get().getId();

        val productDTOList = productRepository.findProductsByUserId(userId);

        List<ProductDetails> productDetailsList = new ArrayList<>();
        byte[] preAlertImage = new byte[0];

        for (ProductDetailsDTO productDTO : productDTOList) {
            if (productDTO.getPreAlertFileName().equalsIgnoreCase("null")){
                preAlertImage = fileUploadService.getFile(productDTO.getPreAlertFileName(), preAlertImageUploadDirectory);
            }
            productDetailsList.add(ProductDetails.builder()
                    .id(productDTO.getId())
                    .supplierName(productDTO.getSupplierName())
                    .weight(productDTO.getWeight())
                    .description(productDTO.getDescription())
                    .customerNumber(productDTO.getCustomerNumber())
                    .trackingNumber(productDTO.getTrackingNumber())
                    .shipmentType(productDTO.getShipmentType())
                    .packageStatus(productDTO.getPackageStatus())
                    .preAlert(preAlertImage)
                    .build());
        }
        return productDetailsList;
    }

    @Override
    public List<ProductDetails> getAllProducts() throws IOException {
        val productDTOList = productRepository.getAllProducts();

        List<ProductDetails> productDetailsList = new ArrayList<>();

        byte[] preAlertImage = new byte[0];


        for (ProductDetailsDTO productDTO : productDTOList) {
            if (productDTO.getPreAlertFileName().equalsIgnoreCase("null")){
                preAlertImage = fileUploadService.getFile(productDTO.getPreAlertFileName(), preAlertImageUploadDirectory);
            }
            productDetailsList.add(ProductDetails.builder()
                    .id(productDTO.getId())
                    .supplierName(productDTO.getSupplierName())
                    .weight(productDTO.getWeight())
                    .description(productDTO.getDescription())
                    .customerNumber(productDTO.getCustomerNumber())
                    .trackingNumber(productDTO.getTrackingNumber())
                    .shipmentType(productDTO.getShipmentType())
                    .packageStatus(productDTO.getPackageStatus())
                    .preAlert(preAlertImage)
                    .build());
        }
        return productDetailsList;
    }

    @Override
    public ProductDetails findProductById(Long packageId) throws IOException {
        val productOptional = productRepository.findProductById(packageId).orElseThrow();

        byte[] preAlertImage;
        if (productOptional.getPreAlertFileName() != null){
            preAlertImage = fileUploadService.getFile(productOptional.getPreAlertFileName(), preAlertImageUploadDirectory);
        }else{
            preAlertImage = new byte[0];
        }
        return ProductDetails.builder()
                .id(productOptional.getId())
                .supplierName(productOptional.getSupplierName())
                .weight(productOptional.getWeight())
                .description(productOptional.getDescription())
                .customerNumber(productOptional.getCustomerNumber())
                .trackingNumber(productOptional.getTrackingNumber())
                .shipmentType(productOptional.getShipmentType())
                .packageStatus(productOptional.getPackageStatus())
                .preAlert(preAlertImage)
                .build();
    }

    @Override
    public void setShipmentType(Long packageId, ShipmentType shipmentType) {
        productRepository.setShipmentType(packageId, shipmentType);
    }

    @Override
    public void updateProductStatus(Long productId, PackageStatus status) {
        productRepository.updateProductStatus(productId, status);
    }

    @Override
    public void addProductsToShipment(List<Long> productIds, Long shipmentId) {
        productRepository.addProductsToShipment(productIds, shipmentId);
    }

    @Override
    public List<ProductDetails> findProductDetailsByShipmentId(Long shipmentId) throws IOException {
        val productDetailsDTOList = productRepository.findProductDetailsByShipmentId(shipmentId);

        List<ProductDetails> productDetailsList = new ArrayList<>();
        byte[] preAlertImage;

        for (ProductDetailsDTO productDTO : productDetailsDTOList) {
            if (productDTO.getPreAlertFileName() != null){
                preAlertImage = fileUploadService.getFile(productDTO.getPreAlertFileName(), preAlertImageUploadDirectory);
            }else {
                preAlertImage = new byte[0];
            }
            productDetailsList.add(ProductDetails.builder()
                    .id(productDTO.getId())
                    .supplierName(productDTO.getSupplierName())
                    .weight(productDTO.getWeight())
                    .description(productDTO.getDescription())
                    .customerNumber(productDTO.getCustomerNumber())
                    .trackingNumber(productDTO.getTrackingNumber())
                    .shipmentType(productDTO.getShipmentType())
                    .packageStatus(productDTO.getPackageStatus())
                    .preAlert(preAlertImage)
                    .build());
        }
        return productDetailsList;
    }
}
