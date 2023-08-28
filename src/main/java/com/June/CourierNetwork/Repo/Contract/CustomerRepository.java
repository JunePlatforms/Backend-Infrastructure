package com.June.CourierNetwork.Repo.Contract;


import com.June.CourierNetwork.Model.Customer;

import java.util.Optional;

public interface CustomerRepository {

    public Optional<Customer> findByUserId(long userId);

}
