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
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
            "dd.special_instructions, " +
            "cu.first_name AS customerFirstName, " +
            "cu.last_name AS customerLastName, " +
            "cu.phone_number AS customerPhoneNumber, " +
            "cu.id AS customerId, " +
            "dd.status, " +
            "co.first_name AS courierFirstName, " +
            "co.last_name AS courierLastName, " +
            "co.phone_number AS courierPhoneNumber, " +
            "co.id AS courierId, " +
            "COUNT(cpd.tracking_number) AS totalPackages, " +
            "GROUP_CONCAT(DISTINCT cpd.tracking_number SEPARATOR '~') AS trackingNumbers," +
            "GROUP_CONCAT(cpd.description SEPARATOR '~') AS descriptions, " +
            "GROUP_CONCAT(cpd.id SEPARATOR '~') AS productIds " +
            "FROM delivery_details AS dd " +
            "INNER JOIN user AS cu ON dd.customer_id = cu.id " +
            "LEFT JOIN user AS co ON dd.courier_id = co.id " +
            "INNER JOIN delivery_products AS dp ON dd.id = dp.delivery_id " +
            "LEFT JOIN customer_product_details AS cpd ON dp.product_id = cpd.id ";

    @Override
    public Long save(DeliveryDetailsRequest deliveryDetails) {
        val sql = "INSERT INTO delivery_details " +
                "(pick_up_location, drop_off_location, special_instructions, " +
                "customer_id, status, delivery_date_time) " +
                "VALUES (:pickUpLocation, :dropOffLocation, :specialInstructions, " +
                ":customerId, :status, :deliveryDateTime);";

        String pickUpLocation = locationHelper(deliveryDetails.getPickUpLocation());

        String dropOffLocation = locationHelper(deliveryDetails.getDropOffLocation());

        val params = new MapSqlParameterSource();
        params.addValue("pickUpLocation", pickUpLocation);
        params.addValue("dropOffLocation", dropOffLocation);
        params.addValue("specialInstructions", deliveryDetails.getSpecialInstructions());
        params.addValue("customerId", deliveryDetails.getCustomerId());
        params.addValue("status", DeliveryStatus.PENDING.name());
        params.addValue("deliveryDateTime", deliveryDetails.getDeliveryDateTime());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(sql, params, keyHolder, new String[]{"id"});

        return keyHolder.getKey().longValue();
    }

    @Override
    public void addProduct(Long deliveryId, Long productId) {
        val sql = "INSERT INTO delivery_products " +
                "(delivery_id, product_id) " +
                "VALUES (:deliveryId, :productId)";

        val params = new MapSqlParameterSource();
        params.addValue("deliveryId", deliveryId);
        params.addValue("productId", productId);

        jdbcTemplate.update(sql, params);
    }

    @Override
    public Optional<List<DeliveryDetails>> getAllDeliveryDetails(DeliveryStatus status) {
        val sql = sqlJoinClause + "WHERE dd.status = :status " + "GROUP BY dd.id";

        val params = new MapSqlParameterSource();
        params.addValue("status", status.name());

        return Optional.of(jdbcTemplate.query(sql, params, new DeliveryDetailsMapper()));
    }

    @Override
    public Optional<DeliveryDetails> getDeliveryDetailsByPackageId(Long productId) {
        val sql = sqlJoinClause + "WHERE dp.product_id = :productId " + "GROUP BY dd.id";

        val params = new MapSqlParameterSource();
        params.addValue("productId", productId);

        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, params, new DeliveryDetailsMapper()));
    }

    @Override
    public Optional<DeliveryDetails> getDeliveryDetailsById(Long deliveryId) {
        val sql = sqlJoinClause + "WHERE dd.id = :deliveryId " + "GROUP BY dd.id";

        val params = new MapSqlParameterSource();
        params.addValue("deliveryId", deliveryId);

        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, params, new DeliveryDetailsMapper()));
    }

    @Override
    public Optional<List<DeliveryDetails>> getAllDeliveryDetailsByCustomerId(Long customerId) {
        val sql = sqlJoinClause + "WHERE dd.customer_id = :customerId " + "GROUP BY dd.id";

        val params = new MapSqlParameterSource();
        params.addValue("customerId", customerId);

        return Optional.of(jdbcTemplate.query(sql, params, new DeliveryDetailsMapper()));
    }

    @Override
    public Optional<List<DeliveryDetails>> getAllDeliveryDetailsByCourierId(Long courierId) {
        val sql = sqlJoinClause + "WHERE dd.courier_id = :courierId " + "GROUP BY dd.id";

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
    public DeliveryDetails updateDeliveryStatus(Long deliveryId, DeliveryStatus status) {
        val sql = "UPDATE delivery_details SET status = :status WHERE id = :deliveryId";

        val params = new MapSqlParameterSource();
        params.addValue("deliveryId", deliveryId);
        params.addValue("status", status.name());

        jdbcTemplate.update(sql, params);

        if (status == DeliveryStatus.COMPLETED) {
            return getDeliveryDetailsById(deliveryId).orElse(null);
        }
        return null;
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
