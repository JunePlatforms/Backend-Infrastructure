package com.June.CourierNetwork.Controller;

import com.June.CourierNetwork.Model.ProductDetails;
import com.June.CourierNetwork.Model.ProductDetailsRequest;
import com.June.CourierNetwork.Model.ShippingLabel;
import com.June.CourierNetwork.Service.Contract.WarehouseClerkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/warehouse")
public class WarehouseClerkController {

    private final WarehouseClerkService warehouseClerkService;

    @PostMapping("/create/product")
    public ResponseEntity<String> createProduct(@RequestBody ProductDetailsRequest productDetailsRequest) {
        try {
            warehouseClerkService.saveProductDetails(productDetailsRequest);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/product")
    public ResponseEntity<String> updateProduct(Long productId ,@RequestBody ProductDetailsRequest productDetailsRequest) {
        try {
            warehouseClerkService.updateProductDetails(productId, productDetailsRequest);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/product")
    public ResponseEntity<String> deleteProduct(Long productId) {
        try {
            warehouseClerkService.deleteProductDetails(productId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/products/by/email")
    public ResponseEntity<List<ProductDetails>> getProductByEmail(@RequestParam String email){
        try {
            return new ResponseEntity<>(warehouseClerkService.findProductsByEmail(email), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/all/products")
    public ResponseEntity<List<ProductDetails>> getAllProducts(){
        try {
            return new ResponseEntity<>(warehouseClerkService.getAllProducts(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/generate/shipping/label")
    public ResponseEntity<ShippingLabel> generateShippingLabel(@RequestParam Long productId){
        try {
            return new ResponseEntity<>(warehouseClerkService.generateShippingLabel(productId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
