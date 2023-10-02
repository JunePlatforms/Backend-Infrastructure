package com.June.CourierNetwork.Repo.Contract;

import com.June.CourierNetwork.Model.Administrator;
import com.June.CourierNetwork.Model.ShippingLabel;
import com.June.CourierNetwork.Model.WarehouseClerk;

import java.util.Optional;

public interface AdministratorRepository {

    Optional<Administrator> findByUserId(long userId);

    Long save(Administrator administrator);
}
