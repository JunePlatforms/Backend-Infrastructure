package com.June.CourierNetwork.Controller;

import com.June.CourierNetwork.DTO.UserDTO;
import com.June.CourierNetwork.Model.UpdatePasswordRequest;
import com.June.CourierNetwork.Model.UpdateUserRequest;
import com.June.CourierNetwork.Model.User;
import com.June.CourierNetwork.Repo.Contract.UserRepository;
import com.June.CourierNetwork.Service.Contract.UserService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserRepository repository;
    private final UserService service;


    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) throws IOException {
        UserDTO userDTO = service.findUserById(id);

        if (userDTO == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        val user = repository.findUserById(id);
        if (user.isPresent()){
            service.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @Valid @RequestBody UpdateUserRequest userDetails){
        val userOptional = repository.findUserById(id);
        if (userOptional.isPresent()){
            service.updateUser(id, userDetails);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/password/{id}")
    public ResponseEntity<String> updateUserPassword(@PathVariable Long id, @Valid @RequestBody UpdatePasswordRequest passwordRequest){
        val userOptional = repository.findUserById(id);
        if (userOptional.isPresent()){
            service.updateUserPassword(id, passwordRequest);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
