package com.June.CourierNetwork.Controller;

import com.June.CourierNetwork.DTO.UserDTO;
import com.June.CourierNetwork.Model.UpdateUserRequest;
import com.June.CourierNetwork.Repo.Contract.CourierRepository;
import com.June.CourierNetwork.Repo.Contract.UserRepository;
import com.June.CourierNetwork.Service.Contract.CourierService;
import com.June.CourierNetwork.Service.Contract.UserService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/courier")
public class CourierUserController {

    private final UserRepository repository;
    private final CourierService courierService;

    @PutMapping("/{id}/update/rating")
    public ResponseEntity<String> updateCourierRating(@PathVariable Long id, Integer rating){
        val userOptional = repository.findUserById(id);
        if (userOptional.isPresent()){
            courierService.updateRate(id, rating);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}/update/availability")
    public ResponseEntity<String> updateCourierAvailability(@PathVariable Long id, Boolean status){
        val userOptional = repository.findUserById(id);
        if (userOptional.isPresent()){
            courierService.updateAvailability(id, status);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
