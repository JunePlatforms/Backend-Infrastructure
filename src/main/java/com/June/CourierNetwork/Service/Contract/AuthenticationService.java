package com.June.CourierNetwork.Service.Contract;

import com.June.CourierNetwork.Model.AuthenticationRequest;
import com.June.CourierNetwork.Model.AuthenticationResponse;
import com.June.CourierNetwork.Model.RegisterRequest;
import com.June.CourierNetwork.Model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthenticationService {

    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    void saveUserToken(User user, String jwtToken);

    void revokeAllUserTokens(User user);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;


}
