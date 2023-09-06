package com.June.CourierNetwork.Service.Contract;

import com.June.CourierNetwork.DTO.CourierDTO;

import java.util.Optional;

public interface CourierService {
    int updateRate(Long id, Integer rating);

    int updateAvailability(Long id, Boolean status);

    Optional<CourierDTO> findUserById(Long id);

}
