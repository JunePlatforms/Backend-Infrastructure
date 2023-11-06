package com.June.CourierNetwork.Controller;

import com.June.CourierNetwork.Model.AuthenticationRequest;
import com.June.CourierNetwork.Model.AuthenticationResponse;
import com.June.CourierNetwork.Model.RegisterRequest;
import com.June.CourierNetwork.Repo.Contract.UserRepository;
import com.June.CourierNetwork.Service.Contract.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin(origins = "https://june-platforms.web.app")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService service;
  private final UserRepository userRepository;

  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(
          @RequestPart RegisterRequest request,
          @RequestPart MultipartFile policeRecord,
          @RequestPart MultipartFile driversLicense
          ) {
    if (userRepository.findActiveUserByEmail(request.getEmail()).isEmpty()){
        try {
          return ResponseEntity.ok(service.register(request, policeRecord, driversLicense));
        }catch (Exception e){
          e.printStackTrace();
          return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
  }
  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> authenticate(
      @RequestBody AuthenticationRequest request
  ) {
    return ResponseEntity.ok(service.authenticate(request));
  }

  @PostMapping("/refresh-token")
  public void refreshToken(
      HttpServletRequest request,
      HttpServletResponse response
  ) throws IOException {
    service.refreshToken(request, response);
  }


}
