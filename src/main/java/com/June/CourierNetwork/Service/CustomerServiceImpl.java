package com.June.CourierNetwork.Service;

import com.June.CourierNetwork.Model.Customer;
import com.June.CourierNetwork.Repo.Contract.CustomerRepository;
import com.June.CourierNetwork.Repo.Contract.UserRepository;
import com.June.CourierNetwork.Service.Contract.CustomerService;
import com.June.CourierNetwork.Service.Contract.FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final FileUploadService fileUploadService;
    @Value("${profile.image.upload.dir}")
    private String profileImageUploadDirectory;


    @Override
    public String generateCustomerNumber(long userId) {
        return "JUNE00" + userId;
    }

    @Override
    public String generateMailBox(long userId) {
        return "JuneMB00" + userId;
    }

    @Override
    public List<Customer> getAllCustomers() throws IOException {
       val customerDTOs = customerRepository.findAll();

       List<Customer> customers = new ArrayList<>();
       for (val customerDTO : customerDTOs){
           val user = userRepository.findUserById(customerDTO.getUserId()).orElseThrow();
           byte[] profileImage = fileUploadService.getFile(customerDTO.getProfilePicture(), profileImageUploadDirectory);

           customers.add(Customer.builder()
                   .username(customerDTO.getUsername())
                   .customerNumber(customerDTO.getCustomerNumber())
                   .mailBox(customerDTO.getMailBox())
                   .profilePicture(profileImage)
                   .user(user)
                   .build());
       }
       return customers;
    }

    @Override
    public Long save(Customer customer) {
        long userId;
        String customerNumber;
        String mailBox;

        try {
             userId = userRepository.save(customer.getUser());
             customerNumber = generateCustomerNumber(userId);
             mailBox = generateMailBox(userId);

             customer.setCustomerNumber(customerNumber);
             customer.setMailBox(mailBox);
        }catch (Exception e){
            return null;
        }
        return customerRepository.save(customer, userId);
    }
}
