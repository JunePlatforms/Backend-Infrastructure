package com.June.CourierNetwork.Controller;

import com.June.CourierNetwork.DTO.UserDTO;
import com.June.CourierNetwork.Model.User;
import com.June.CourierNetwork.Repo.Contract.UserRepository;
import com.June.CourierNetwork.Service.Contract.UserService;
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
    private final UserService service;

    @PostMapping
    public ResponseEntity<String> createUserAccount(@RequestBody @Valid User user){
        repository.save(user);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id){
        UserDTO userDTO = service.findUserById(id);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
}
