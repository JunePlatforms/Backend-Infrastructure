package com.June.CourierNetwork.Service.Contract;

public interface CustomerService {
    String generateCustomerNumber(long userId);

    String generateMailBox(long userId);
}
