package com.June.CourierNetwork.Repo.Contract;

import com.June.CourierNetwork.DTO.CourierDTO;
import com.June.CourierNetwork.Model.Courier;

import java.util.Optional;

public interface CourierRepository {

    Long updateRating(Long id, Courier courier);

    int updateAvailability(Long id, Boolean status);

    Optional<CourierDTO> findByUserId(long userId);

    Long save(Courier courier);

    int updateRate(Long id, Integer rating);

    String getPoliceRecord(Long userId);

    void updatePoliceRecord(String newFileName, Long userId);

    String getDriversLicense(Long userId);

    void updateDriversLicense(String newFileName, Long userId);
}
