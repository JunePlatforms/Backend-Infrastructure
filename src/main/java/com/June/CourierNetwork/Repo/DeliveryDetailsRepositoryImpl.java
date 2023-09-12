package com.June.CourierNetwork.Repo;

import com.June.CourierNetwork.Enum.DeliveryStatus;
import com.June.CourierNetwork.Model.DeliveryDetailsRequest;
import com.June.CourierNetwork.Repo.Contract.DeliveryDetailsRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DeliveryDetailsRepositoryImpl implements DeliveryDetailsRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public void save(DeliveryDetailsRequest deliveryDetailsRequest) {
        val sql = "INSERT INTO delivery_details " +
                "(pick_up_location, drop_off_location, package_description, special_instructions, " +
                "customer_id, status, package_id) " +
                "VALUES (:pickUpLocation, :dropOffLocation, :packageDescription, :specialInstructions, " +
                ":customerId, :status, :packageId)";

        String pickUpLocation = deliveryDetailsRequest.getPickUpLocation().getLine1() + "_" +
                deliveryDetailsRequest.getPickUpLocation().getLine2() + "_" +
                deliveryDetailsRequest.getPickUpLocation().getCity() + "_" +
                deliveryDetailsRequest.getPickUpLocation().getParish().name();

        String dropOffLocation = deliveryDetailsRequest.getDropOffLocation().getLine1() + "_" +
                deliveryDetailsRequest.getDropOffLocation().getLine2() + "_" +
                deliveryDetailsRequest.getDropOffLocation().getCity() + "_" +
                deliveryDetailsRequest.getDropOffLocation().getParish().name();

        val params = new MapSqlParameterSource();
        params.addValue("pickUpLocation", pickUpLocation);
        params.addValue("dropOffLocation", dropOffLocation);
        params.addValue("packageDescription", deliveryDetailsRequest.getPackageDescription());
        params.addValue("specialInstructions", deliveryDetailsRequest.getSpecialInstructions());
        params.addValue("customerId", deliveryDetailsRequest.getCustomerId());
        params.addValue("status", DeliveryStatus.PENDING.name());
        params.addValue("packageId", deliveryDetailsRequest.getPackageId());

        jdbcTemplate.update(sql, params);
    }

    @Override
    public Optional<List<DeliveryDetailsRequest>> getAllDeliveryDetails() {
        val sql = "SELECT d.*, u.first_name, u.last_name, u.phone_number FROM delivery_details d " +;
    }
}
