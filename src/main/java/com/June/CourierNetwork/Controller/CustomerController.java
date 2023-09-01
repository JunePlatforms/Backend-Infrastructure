package com.June.CourierNetwork.Controller;

import com.June.CourierNetwork.Repo.Contract.UserRepository;
import com.June.CourierNetwork.Service.Contract.CourierService;
import com.June.CourierNetwork.Service.Contract.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

public class CustomerController {

    @RestController
    @RequiredArgsConstructor
    @RequestMapping("/api/v1/user/customer")
    public class CustomerUserController {

        private final UserRepository repository;
        private final CustomerService customerService;

        @PutMapping("/{id}/update/profilePic")
        public ResponseEntity<String> updateCustomerProfilePic(@PathVariable Long id, String profilePic){
            val userOptional = repository.findUserById(id);
            if (userOptional.isPresent()){
                customerService.updateProfilePic(id, profilePic);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
