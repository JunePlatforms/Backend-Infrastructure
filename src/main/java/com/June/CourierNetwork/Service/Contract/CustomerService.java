package com.June.CourierNetwork.Service.Contract;

import com.June.CourierNetwork.DTO.CustomerDTO;
import com.June.CourierNetwork.Model.Customer;

import java.io.IOException;
import java.util.List;

public interface CustomerService {
    String generateCustomerNumber(long userId);

    String generateMailBox(long userId);

    List<Customer> getAllCustomers() throws IOException;

    Long save(Customer customer);
}
