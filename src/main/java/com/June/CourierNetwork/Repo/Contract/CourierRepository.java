package com.June.CourierNetwork.Repo.Contract;

import com.June.CourierNetwork.Model.Courier;

import java.util.Optional;

public interface CourierRepository {

    Long updateRating(Long id, Courier courier);

    Long updateAvailability(Long id, Courier courier);

    Optional<Courier> findByUserId(long userId);

    Long save(Courier courier);
}
