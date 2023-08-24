package com.June.CourierNetwork.Controller;

import com.June.CourierNetwork.DTO.UserDTO;
import com.June.CourierNetwork.Model.User;
import com.June.CourierNetwork.Repo.Contract.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserRepository repository;

    @PostMapping
    public ResponseEntity<String> createUserAccount(@RequestBody @Valid User user){
        repository.save(user);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }
}
