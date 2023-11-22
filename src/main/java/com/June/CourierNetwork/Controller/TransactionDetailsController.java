package com.June.CourierNetwork.Controller;

import com.June.CourierNetwork.DTO.TransactionDetailsDTO;
import com.June.CourierNetwork.Service.Contract.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "https://app.junelogistics.com")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transaction/details")
public class TransactionDetailsController {

    private final TransactionService transactionService;

    @GetMapping("get/by/customer/number")
    public ResponseEntity<TransactionDetailsDTO> getTransactionDetailsByCustomerNumber(@RequestParam("customerNumber") String customerNumber) {
        try {
            return new ResponseEntity<>(transactionService.getTransactionDetailsByCustomerNumber(customerNumber), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("get/all")
    public ResponseEntity<List<TransactionDetailsDTO>> getAllTransactionDetails() {
        try {
            return new ResponseEntity<>(transactionService.getAllTransactionDetails(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
