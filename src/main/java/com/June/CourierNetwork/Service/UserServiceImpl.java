package com.June.CourierNetwork.Service;

import com.June.CourierNetwork.DTO.CourierDTO;
import com.June.CourierNetwork.DTO.CustomerDTO;
import com.June.CourierNetwork.DTO.UserDTO;
import com.June.CourierNetwork.Enum.Role;
import com.June.CourierNetwork.Model.*;
import com.June.CourierNetwork.Repo.Contract.*;
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
    private final AdministratorRepository administratorRepository;
    private final PasswordEncoder passwordEncoder;
    @Value("${profile.image.upload.dir}")
    private String profileImageUploadDirectory;

    @Value("${police.record.upload.dir}")
    private String policeRecordUploadDirectory;

    @Value("${drivers.license.upload.dir}")
    private String driversLicenseUploadDirectory;

    @Override
    public UserDTO findUserByEmail(String email) throws IOException {
        UserDTO userDTO = new UserDTO();
        val user = userRepository.findUserByEmail(email);
        val id = user.get().getId();

        if (user.isPresent()) {
            if (user.get().getRole().equals(Role.COURIER)) {
                Optional<CourierDTO> courierDTO = courierRepository.findByUserId(id);
                byte[] policeRecord = fileUploadService.getFile(courierDTO.orElseThrow().getPoliceRecord(), policeRecordUploadDirectory);
                byte[] driversLicense = fileUploadService.getFile(courierDTO.orElseThrow().getDriversLicense(), driversLicenseUploadDirectory);

                Courier courier = Courier.builder()
                        .assessmentScore(courierDTO.orElseThrow().getAssessmentScore())
                        .acceptedTermsAndConditions(courierDTO.orElseThrow().getAcceptedTermsAndConditions())
                        .courierId(courierDTO.orElseThrow().getCourierId())
                        .isAvailable(courierDTO.orElseThrow().getIsAvailable())
                        .rating(courierDTO.orElseThrow().getRating())
                        .reason(courierDTO.orElseThrow().getReason())
                        .driversLicense(driversLicense)
                        .policeRecord(policeRecord)
                        .applicationStatus(courierDTO.orElseThrow().getApplicationStatus())
                        .licensePlateNumber(courierDTO.orElseThrow().getLicensePlateNumber())
                        .vehicleMake(courierDTO.orElseThrow().getVehicleMake())
                        .vehicleModel(courierDTO.orElseThrow().getVehicleModel())
                        .vehicleType(courierDTO.orElseThrow().getVehicleType())
                        .user(user.orElseThrow())
                        .build();
                userDTO.setCourier(courier);
            }
            else if (user.get().getRole().equals(Role.WAREHOUSE_CLERK)) {
                Optional<WarehouseClerk> warehouseClerk = warehouseClerkRepository.findByUserId(id);
                warehouseClerk.orElseThrow().setUser(user.orElseThrow());
                userDTO.setWarehouseClerk(warehouseClerk.orElseThrow());
            }
            else if (user.get().getRole().equals(Role.ADMIN)) {
                Optional<Administrator> administrator = administratorRepository.findByUserId(id);
                administrator.orElseThrow().setUser(user.orElseThrow());
                userDTO.setAdministrator(administrator.orElseThrow());
            }
            else if (user.get().getRole().equals(Role.CUSTOMER)) {
                Optional<CustomerDTO> optionalCustomerDTO = customerRepository.findByUserId(id);
                CustomerDTO customerDTO = optionalCustomerDTO.orElseThrow();
                customerDTO.setUser(user.orElseThrow());
                byte[] profileImage = fileUploadService.getFile(customerDTO.getProfilePicture(), profileImageUploadDirectory);

                Customer customer = Customer.builder()
                        .username(customerDTO.getUsername())
                        .user(user.orElseThrow())
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
