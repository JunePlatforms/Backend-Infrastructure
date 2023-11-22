package com.June.CourierNetwork.Controller;

import com.June.CourierNetwork.DTO.ShipmentDTO;
import com.June.CourierNetwork.Enum.PackageStatus;
import com.June.CourierNetwork.Model.ProductDetails;
import com.June.CourierNetwork.Model.ProductDetailsRequest;
import com.June.CourierNetwork.Model.Shipment;
import com.June.CourierNetwork.Model.ShippingLabel;
import com.June.CourierNetwork.Service.Contract.FileUploadService;
import com.June.CourierNetwork.Service.Contract.ProductService;
import com.June.CourierNetwork.Service.Contract.ShipmentService;
import com.June.CourierNetwork.Service.Contract.WarehouseClerkService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "https://app.junelogistics.com")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/warehouse")
public class WarehouseClerkController {

    private final WarehouseClerkService warehouseClerkService;
    private final ProductService productService;
    private final ShipmentService shipmentService;
    private final FileUploadService fileUploadService;
    @Value("${airway.invoice.upload.dir}")
    private String airWayInvoiceUploadDirectory;

    @Value("${shipment.manifest.upload.dir}")
    private String shipmentManifestUploadDirectory;


    @PostMapping("/create/product")
    public ResponseEntity<String> createProduct(@RequestBody ProductDetailsRequest productDetailsRequest) {
        try {
            productService.saveProductDetails(productDetailsRequest);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/product")
    public ResponseEntity<String> updateProduct(Long productId ,@RequestBody ProductDetailsRequest productDetailsRequest) {
        try {
            productService.updateProductDetails(productId, productDetailsRequest);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("batch/add/products/to/shipment/{shipmentId}")
    public ResponseEntity<String> addProductToShipment(@RequestBody List<Long> productIds, @PathVariable Long shipmentId) {
        try {
            productService.addProductsToShipment(productIds, shipmentId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/delete/product")
    public ResponseEntity<String> deleteProduct(Long productId) {
        try {
            productService.deleteProductDetails(productId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/products/by/email")
    public ResponseEntity<List<ProductDetails>> getProductByEmail(@RequestParam String email){
        try {
            return new ResponseEntity<>(productService.findProductsByEmail(email), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/all/products")
    public ResponseEntity<List<ProductDetails>> getAllProducts(){
        try {
            return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
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

    @PutMapping("/update/product/status")
    public ResponseEntity<String> updateProductStatus(@RequestParam Long productId, @RequestParam PackageStatus status){
        try {
            productService.updateProductStatus(productId, status);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("{shipmentId}/upload/airway/invoice")
    public ResponseEntity<String> uploadAirwayInvoice(@PathVariable Long shipmentId, MultipartFile file) throws IOException {
        shipmentService.findShipmentById(shipmentId);
        try {
            fileUploadService.uploadAirWayInvoice(file, shipmentId, airWayInvoiceUploadDirectory);
            return ResponseEntity.ok("File uploaded successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file: " + e.getMessage());
        }
    }

    @PutMapping("{shipmentId}/upload/shipment/manifest")
    public ResponseEntity<String> uploadShipmentManifest(@PathVariable Long shipmentId, MultipartFile file) throws IOException {
        shipmentService.findShipmentById(shipmentId);
        try {
            fileUploadService.uploadShipmentManifest(file, shipmentId, shipmentManifestUploadDirectory);
            return ResponseEntity.ok("File uploaded successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file: " + e.getMessage());
        }
    }

    @PostMapping("/create/shipment")
    public ResponseEntity<String> createShipment
            (@RequestPart ShipmentDTO shipmentDTO,
             @RequestPart MultipartFile airwayInvoice,
             @RequestPart MultipartFile shipmentManifest)
    {
        try {
            shipmentService.createShipment(shipmentDTO, airwayInvoice, shipmentManifest);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/all/shipments")
    public ResponseEntity<List<Shipment>> getAllShipments(){
        try {
            return new ResponseEntity<>(shipmentService.getAllShipments(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/shipment/{shipmentId}")
    public ResponseEntity<Shipment> findShipmentById(@PathVariable Long shipmentId){
        try {
            return new ResponseEntity<>(shipmentService.findShipmentById(shipmentId), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/shipment/{shipmentId}")
    public ResponseEntity<String> updateShipment(@PathVariable Long shipmentId, @RequestBody ShipmentDTO shipmentDTO){
        try {
            shipmentService.updateShipment(shipmentId, shipmentDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/shipment/{shipmentId}")
    public ResponseEntity<String> deleteShipment(@PathVariable Long shipmentId){
        try {
            shipmentService.deleteShipment(shipmentId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
