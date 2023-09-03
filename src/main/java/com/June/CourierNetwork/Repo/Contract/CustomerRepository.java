package com.June.CourierNetwork.Repo.Contract;

import com.June.CourierNetwork.DTO.CustomerDTO;
import com.June.CourierNetwork.Model.Customer;

import java.util.Optional;

public interface CustomerRepository {
    Long save(Customer customer);

    int updateProfileImage(Long id, String fileName);


    String getProfileImage(Long userId);

    Optional<CustomerDTO> findByUserId(Long userId);
}
