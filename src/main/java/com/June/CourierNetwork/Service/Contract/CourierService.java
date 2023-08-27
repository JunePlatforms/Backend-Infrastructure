package com.June.CourierNetwork.Service.Contract;

public interface CourierService {
    int updateRate(Long id, Integer rating);

    int updateAvailability(Long id, Boolean status);

}
