package com.June.CourierNetwork.Service;

import com.June.CourierNetwork.DTO.CourierDTO;
import com.June.CourierNetwork.DTO.CustomerDTO;
import com.June.CourierNetwork.DTO.UserDTO;
import com.June.CourierNetwork.Enum.Role;
import com.June.CourierNetwork.Model.*;
import com.June.CourierNetwork.Repo.Contract.CourierRepository;
import com.June.CourierNetwork.Repo.Contract.CustomerRepository;
import com.June.CourierNetwork.Repo.Contract.UserRepository;
import com.June.CourierNetwork.Repo.Contract.WarehouseClerkRepository;
import com.June.CourierNetwork.Service.Contract.FileUploadService;
import com.June.CourierNetwork.Service.Contract.UserService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CourierRepository courierRepository;
    private final CustomerRepository customerRepository;
    private final FileUploadService fileUploadService;
    private final WarehouseClerkRepository warehouseClerkRepository;
    private final PasswordEncoder passwordEncoder;
    @Value("${profile.image.upload.dir}")
    private String profileImageUploadDirectory;

    @Override
    public UserDTO findUserById(Long userId) throws IOException {
        UserDTO userDTO = new UserDTO();
        val user = userRepository.findUserById(userId);

        if (user.isPresent()) {
            if (user.get().getRole().equals(Role.COURIER)) {
                Optional<CourierDTO> courierDTO = courierRepository.findByUserId(userId);
                Courier courier = Courier.builder()
                        .assessmentScore(courierDTO.orElseThrow().getAssessmentScore())
                        .acceptedTermsAndConditions(courierDTO.orElseThrow().getAcceptedTermsAndConditions())
                        .courierId(courierDTO.orElseThrow().getCourierId())
                        .isAvailable(courierDTO.orElseThrow().getIsAvailable())
                        .rating(courierDTO.orElseThrow().getRating())
                        .user(user.orElseThrow())
                        .build();
                userDTO.setCourier(courier);
            } else if (user.get().getRole().equals(Role.WAREHOUSE_CLERK)) {
                Optional<WarehouseClerk> warehouseClerk = warehouseClerkRepository.findByUserId(userId);
                warehouseClerk.orElseThrow().setUser(user.orElseThrow());
                userDTO.setWarehouseClerk(warehouseClerk.orElseThrow());
            } else if (user.get().getRole().equals(Role.CUSTOMER)) {
                Optional<CustomerDTO> optionalCustomerDTO = customerRepository.findByUserId(userId);
                CustomerDTO customerDTO = optionalCustomerDTO.orElseThrow();
                customerDTO.setUser(user.orElseThrow());
                byte[] profileImage = fileUploadService.getFile(customerDTO.getProfilePicture(), profileImageUploadDirectory);

                Customer customer = Customer.builder()
                        .username(customerDTO.getUsername())
                        .user(user.orElseThrow())
                        .customerId(customerDTO.getId())
                        .customerNumber(customerDTO.getCustomerNumber())
                        .profilePicture(profileImage)
                        .acceptedTermsAndConditions(customerDTO.getAcceptedTermsAndConditions())
                        .mailBox(customerDTO.getMailBox())
                        .build();
                userDTO.setCustomer(customer);
            }
        }else {
            return null;
        }
        return userDTO;
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public int updateUser(Long id, UpdateUserRequest userDetails) {
        return userRepository.updateUser(id, userDetails);
    }

    @Override
    public int updateUserPassword(Long id, UpdatePasswordRequest passwordRequest) {
        val oldPassword = userRepository.getUserPassword(id);
        val newEncodedPassword = passwordEncoder.encode(passwordRequest.getNewPassword());

        if (oldPassword.isPresent() && (passwordEncoder.matches(passwordRequest.getOldPassword(), oldPassword.get().getOldPassword()))){
            passwordRequest.setNewPassword(newEncodedPassword);
            return userRepository.updateUserPassword(id, passwordRequest);
        }
        return 0;
    }
}
