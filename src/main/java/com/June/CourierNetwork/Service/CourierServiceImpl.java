package com.June.CourierNetwork.Service;

import com.June.CourierNetwork.DTO.UserDTO;
import com.June.CourierNetwork.Enum.Role;
import com.June.CourierNetwork.Model.Courier;
import com.June.CourierNetwork.Model.UpdateUserRequest;
import com.June.CourierNetwork.Repo.Contract.CourierRepository;
import com.June.CourierNetwork.Repo.Contract.UserRepository;
import com.June.CourierNetwork.Service.Contract.CourierService;
import com.June.CourierNetwork.Service.Contract.UserService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourierServiceImpl implements CourierService {
    private final UserRepository userRepository;
    private final CourierRepository courierRepository;

    @Override
    public int updateRate(Long id, Integer rating) {
        return courierRepository.updateRate(id, rating);
    }

    @Override
    public int updateAvailability(Long id, Boolean status) {
        return courierRepository.updateAvailability(id, status);}
}
