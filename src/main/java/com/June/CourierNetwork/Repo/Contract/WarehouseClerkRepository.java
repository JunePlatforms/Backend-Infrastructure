package com.June.CourierNetwork.Repo.Contract;

import com.June.CourierNetwork.Model.Courier;
import com.June.CourierNetwork.Model.WarehouseClerk;

import java.util.Optional;

public interface WarehouseClerkRepository {

    Optional<WarehouseClerk> findByUserId(long userId);

    Long save(WarehouseClerk warehouseClerk);

}
