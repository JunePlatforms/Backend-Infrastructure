package com.June.CourierNetwork.Controller;

import com.June.CourierNetwork.Model.DeliveryDetailsRequest;
import com.June.CourierNetwork.Service.Contract.DeliveryDetailsService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/delivery/details")
public class DeliveryDetailsController {

    private final DeliveryDetailsService deliveryDetailsService;

    @PostMapping("/create")
    public ResponseEntity<String> createDeliveryDetails(@RequestBody DeliveryDetailsRequest deliveryDetailsRequest) {
        try {
            deliveryDetailsService.saveDeliveryDetails(deliveryDetailsRequest);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<DeliveryDetailsRequest>> getAllDeliveryDetails() {
        try {
            return new ResponseEntity<>(deliveryDetailsService.getAllDeliveryDetails(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
