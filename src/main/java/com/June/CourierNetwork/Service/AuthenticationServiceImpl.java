package com.June.CourierNetwork.Service;

import com.June.CourierNetwork.Enum.Role;
import com.June.CourierNetwork.Enum.TokenType;
import com.June.CourierNetwork.Model.*;
import com.June.CourierNetwork.Repo.Contract.*;
import com.June.CourierNetwork.Service.Contract.AuthenticationService;
import com.June.CourierNetwork.Service.Contract.CustomerService;
import com.June.CourierNetwork.Service.Contract.FileUploadService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final CourierRepository courierRepository;
    private final CustomerService customerService;
    private final WarehouseClerkRepository warehouseClerkRepository;
    private final AdministratorRepository administratorRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final FileUploadService fileUploadService;
    @Value("${police.record.upload.dir}")
    private String policeRecordUploadDirectory;

    @Value("${drivers.license.upload.dir}")
    private String driversLicenseUploadDirectory;

    @Override
    public AuthenticationResponse register(RegisterRequest request){
        Long savedUserId = null;
        var user = User.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .emailAddress(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .role(request.getRole())
                .build();

        if (request.getRole().equals(Role.COURIER)) {
            var courier = Courier.builder()
                    .assessmentScore(request.getAssessmentScore())
                    .acceptedTermsAndConditions(request.getAcceptedTermsAndConditions())
                    .vehicleMake(request.getVehicleMake())
                    .vehicleModel(request.getVehicleModel())
                    .vehicleType(request.getVehicleType())
                    .licensePlateNumber(request.getLicensePlateNumber())
                    .user(user)
                    .build();
            savedUserId = courierRepository.save(courier);
        }
        else if (request.getRole().equals(Role.WAREHOUSE_CLERK)) {
            var warehouseClerk = WarehouseClerk.builder()
                    .user(user)
                    .build();
            savedUserId = warehouseClerkRepository.save(warehouseClerk);
        }
        else if (request.getRole().equals(Role.CUSTOMER)) {
            var customer = Customer.builder()
                    .username(request.getUsername())
                    .user(user)
                    .acceptedTermsAndConditions(request.getAcceptedTermsAndConditions())
                    .build();
            savedUserId = customerService.save(customer);
        }
        else if (request.getRole().equals(Role.ADMIN)) {
            var admin = Administrator.builder()
                    .user(user)
                    .build();
            savedUserId = administratorRepository.save(admin);
        }

        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUserId, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findActiveUserByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user.getId(), jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public void saveUserToken(Long userId, String jwtToken) {
        var token = Token.builder()
                .userId(userId)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    @Override
    public void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(Math.toIntExact(user.getId()));
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.userRepository.findUserByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user.getId(), accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    private boolean emailExists(String email){
        return userRepository.findActiveUserByEmail(email).isPresent();
    }
}
