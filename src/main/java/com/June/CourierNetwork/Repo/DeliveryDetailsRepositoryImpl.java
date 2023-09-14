package com.June.CourierNetwork.Repo;

import com.June.CourierNetwork.Enum.DeliveryStatus;
import com.June.CourierNetwork.Mapper.DeliveryDetailsMapper;
import com.June.CourierNetwork.Model.DeliveryDetails;
import com.June.CourierNetwork.Model.DeliveryDetailsRequest;
import com.June.CourierNetwork.POJO.Address;
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

    private static final String sqlJoinClause = "SELECT " +
            "dd.pick_up_location, " +
            "dd.drop_off_location, " +
            "dd.delivery_date_time, " +
            "cpd.description, " +
            "dd.special_instructions, " +
            "cu.first_name AS customerFirstName, " +
            "cu.last_name AS customerLastName, " +
            "cu.phone_number AS customerPhoneNumber, " +
            "dd.status, " +
            "co.first_name AS courierFirstName, " +
            "co.last_name AS courierLastName, " +
            "co.phone_number AS courierPhoneNumber " +
            "FROM delivery_details AS dd " +
            "INNER JOIN user AS cu ON dd.customer_id = cu.id " +
            "LEFT JOIN user AS co ON dd.courier_id = co.id " +
            "LEFT JOIN customer_product_details AS cpd ON dd.package_id = cpd.id ";

    @Override
    public void save(DeliveryDetailsRequest deliveryDetails) {
        val sql = "INSERT INTO delivery_details " +
                "(pick_up_location, drop_off_location, special_instructions, " +
                "customer_id, status, package_id, delivery_date_time) " +
                "VALUES (:pickUpLocation, :dropOffLocation, :specialInstructions, " +
                ":customerId, :status, :packageId, :deliveryDateTime);";

        String pickUpLocation = locationHelper(deliveryDetails.getPickUpLocation());

        String dropOffLocation = locationHelper(deliveryDetails.getDropOffLocation());

        val params = new MapSqlParameterSource();
        params.addValue("pickUpLocation", pickUpLocation);
        params.addValue("dropOffLocation", dropOffLocation);
        params.addValue("specialInstructions", deliveryDetails.getSpecialInstructions());
        params.addValue("customerId", deliveryDetails.getCustomerId());
        params.addValue("status", DeliveryStatus.PENDING.name());
        params.addValue("packageId", deliveryDetails.getPackageId());
        params.addValue("deliveryDateTime", deliveryDetails.getDeliveryDateTime());

        jdbcTemplate.update(sql, params);
    }

    @Override
    public Optional<List<DeliveryDetails>> getAllDeliveryDetails(DeliveryStatus status) {
        val sql = sqlJoinClause + "WHERE dd.status = :status";

        val params = new MapSqlParameterSource();
        params.addValue("status", status.name());

        return Optional.of(jdbcTemplate.query(sql, params, new DeliveryDetailsMapper()));
    }

    @Override
    public Optional<DeliveryDetails> getDeliveryDetailsByPackageId(Long packageId) {
        val sql = sqlJoinClause + "WHERE dd.package_id = :packageId";

        val params = new MapSqlParameterSource();
        params.addValue("packageId", packageId);

        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, params, new DeliveryDetailsMapper()));
    }

    @Override
    public Optional<List<DeliveryDetails>> getAllDeliveryDetailsByCustomerId(Long customerId) {
        val sql = sqlJoinClause + "WHERE dd.customer_id = :customerId";

        val params = new MapSqlParameterSource();
        params.addValue("customerId", customerId);

        return Optional.of(jdbcTemplate.query(sql, params, new DeliveryDetailsMapper()));
    }

    @Override
    public Optional<List<DeliveryDetails>> getAllDeliveryDetailsByCourierId(Long courierId) {
        val sql = sqlJoinClause + "WHERE dd.courier_id = :courierId";

        val params = new MapSqlParameterSource();
        params.addValue("courierId", courierId);

        return Optional.of(jdbcTemplate.query(sql, params, new DeliveryDetailsMapper()));
    }

    @Override
    public void assignCourier(Long deliveryId, Long courierId) {
        val sql = "UPDATE delivery_details SET courier_id = :courierId WHERE id = :deliveryId";

        val params = new MapSqlParameterSource();
        params.addValue("deliveryId", deliveryId);
        params.addValue("courierId", courierId);

        jdbcTemplate.update(sql, params);
    }

    @Override
    public void updateDeliveryStatus(Long deliveryId, DeliveryStatus status) {
        val sql = "UPDATE delivery_details SET status = :status WHERE id = :deliveryId";

        val params = new MapSqlParameterSource();
        params.addValue("deliveryId", deliveryId);
        params.addValue("status", status.name());

        jdbcTemplate.update(sql, params);
    }

    @Override
    public void updateDeliveryDetails(Long deliveryId, DeliveryDetailsRequest deliveryDetailsRequest) {
        val sql = "UPDATE delivery_details SET " +
                "pick_up_location = :pickUpLocation, " +
                "drop_off_location = :dropOffLocation, " +
                "special_instructions = :specialInstructions, " +
                "delivery_date_time = :deliveryDateTime " +
                "WHERE id = :deliveryId";

        String pickUpLocation = locationHelper(deliveryDetailsRequest.getPickUpLocation());

        String dropOffLocation = locationHelper(deliveryDetailsRequest.getDropOffLocation());

        val params = new MapSqlParameterSource();
        params.addValue("pickUpLocation", pickUpLocation);
        params.addValue("dropOffLocation", dropOffLocation);
        params.addValue("specialInstructions", deliveryDetailsRequest.getSpecialInstructions());
        params.addValue("deliveryDateTime", deliveryDetailsRequest.getDeliveryDateTime());
        params.addValue("deliveryId", deliveryId);

        jdbcTemplate.update(sql, params);
    }

    private String locationHelper(Address address) {
        return address.getLine1() + "_" + address.getLine2() + "_"
                + address.getCity() + "_" + address.getParish();
    }
}
