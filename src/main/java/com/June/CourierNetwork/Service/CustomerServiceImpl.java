package com.June.CourierNetwork.Service;

import com.June.CourierNetwork.Repo.Contract.CourierRepository;
import com.June.CourierNetwork.Repo.Contract.UserRepository;
import com.June.CourierNetwork.Service.Contract.CourierService;
import com.June.CourierNetwork.Service.Contract.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final UserRepository userRepository;
    private final CourierRepository courierRepository;


    @Override
    public String generateCustomerNumber(long userId) {
        return "June00" + userId;
    }

    @Override
    public String generateMailBox(long userId) {
        return "JuneMB00" + userId;
    }
}
