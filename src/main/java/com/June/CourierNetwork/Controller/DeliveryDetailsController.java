package com.June.CourierNetwork.Controller;

import com.June.CourierNetwork.DTO.DeliveryDetailsDTO;
import com.June.CourierNetwork.DTO.DeliveryDetailsRequestDTO;
import com.June.CourierNetwork.Enum.DeliveryStatus;
import com.June.CourierNetwork.Enum.PaymentType;
import com.June.CourierNetwork.Service.Contract.DeliveryDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/delivery/details")
public class DeliveryDetailsController {

    private final DeliveryDetailsService deliveryDetailsService;

    @PostMapping("/create")
    public ResponseEntity<String> createDeliveryDetails(@RequestBody DeliveryDetailsRequestDTO deliveryDetailsRequestDTO) {
        try {
            deliveryDetailsService.saveDeliveryDetails(deliveryDetailsRequestDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/all/by/status")
    public ResponseEntity<List<DeliveryDetailsDTO>> getAllDeliveryDetails( @RequestParam("status") DeliveryStatus status) {
        try {
            return new ResponseEntity<>(deliveryDetailsService.getAllDeliveryDetails(status), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/by/package/{packageId}")
    public ResponseEntity<DeliveryDetailsDTO> getDeliveryDetailsByPackageId(@PathVariable Long packageId) {
        try {
            return new ResponseEntity<>(deliveryDetailsService.getDeliveryDetailsByPackageId(packageId), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/all/by/customer/{customerId}")
    public ResponseEntity<List<DeliveryDetailsDTO>> getAllDeliveryDetailsByCustomerId(@PathVariable Long customerId) {
        try {
            return new ResponseEntity<>(deliveryDetailsService.getAllDeliveryDetailsByCustomerId(customerId), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/all/by/courier/{courierId}")
    public ResponseEntity<List<DeliveryDetailsDTO>> getAllDeliveryDetailsByCourierId(@PathVariable Long courierId) {
        try {
            return new ResponseEntity<>(deliveryDetailsService.getAllDeliveryDetailsByCourierId(courierId), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{deliveryId}/assign/courier")
    public ResponseEntity<String> assignCourier(@PathVariable Long deliveryId, @RequestParam Long courierId) {
        try {
            deliveryDetailsService.assignCourier(deliveryId, courierId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{deliveryId}/update/status")
    public ResponseEntity<String> updateDeliveryStatus
            (@PathVariable Long deliveryId, @RequestParam DeliveryStatus status,
             @RequestParam(required = false) PaymentType paymentType) {
        try {
            deliveryDetailsService.updateDeliveryStatus(deliveryId, status, paymentType);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{deliveryId}/update/delivery")
    public ResponseEntity<String> updateDeliveryDetails(@PathVariable Long deliveryId,
                                                       @RequestBody DeliveryDetailsRequestDTO deliveryDetailsRequestDTO) {
        try {
            deliveryDetailsService.updateDeliveryDetails(deliveryId, deliveryDetailsRequestDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

}
