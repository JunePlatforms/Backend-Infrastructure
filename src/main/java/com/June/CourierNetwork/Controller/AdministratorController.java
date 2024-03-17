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

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/warehouse")
public class AdministratorController {

    private final WarehouseClerkService warehouseClerkService;
    private final ProductService productService;
    private final ShipmentService shipmentService;
    private final FileUploadService fileUploadService;


}
