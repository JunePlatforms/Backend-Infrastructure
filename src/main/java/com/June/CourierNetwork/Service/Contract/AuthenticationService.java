package com.June.CourierNetwork.Service.Contract;

import com.June.CourierNetwork.Model.AuthenticationRequest;
import com.June.CourierNetwork.Model.AuthenticationResponse;
import com.June.CourierNetwork.Model.RegisterRequest;
import com.June.CourierNetwork.Model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AuthenticationService {

    AuthenticationResponse register(RegisterRequest request, MultipartFile policeRecord, MultipartFile driversLicense) throws IOException;

    AuthenticationResponse authenticate(AuthenticationRequest request);

    void saveUserToken(Long userId, String jwtToken);

    void revokeAllUserTokens(User user);

    void deleteAllRevokedTokens(User user);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

    void forgotPassword(String email, String Password, String retypePassword);
}
