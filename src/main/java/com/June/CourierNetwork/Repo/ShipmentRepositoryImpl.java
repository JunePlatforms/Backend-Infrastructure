package com.June.CourierNetwork.Repo;

import com.June.CourierNetwork.DTO.ShipmentDTO;
import com.June.CourierNetwork.Enum.ShipmentStatus;
import com.June.CourierNetwork.Enum.ShipmentType;
import com.June.CourierNetwork.Mapper.ShipmentDTOMapper;
import com.June.CourierNetwork.Repo.Contract.ShipmentRepository;
import com.June.CourierNetwork.Repo.Contract.UserRepository;
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
public class ShipmentRepositoryImpl implements ShipmentRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;


    @Override
    public Long createShipment(ShipmentDTO shipmentDTO) {
        val sql = "INSERT INTO JuneCourierNetwork.shipment " +
                "(shipment_type, status, departure_date, arrival_date, created_on, updated_on) " +
                "VALUES(:shipmentType, :status, :departureDate, :arrivalDate, :createdOn, :updatedOn);";

        val params = new MapSqlParameterSource();
        val now = java.time.LocalDateTime.now();
        params.addValue("shipmentType", shipmentDTO.getType().name());
        params.addValue("status", shipmentDTO.getStatus().name());
        params.addValue("departureDate", shipmentDTO.getDepartureDate());
        params.addValue("arrivalDate", shipmentDTO.getArrivalDate());
        params.addValue("createdOn", now);
        params.addValue("updatedOn", now);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(sql, params, keyHolder, new String[]{"id"});

        return keyHolder.getKey().longValue();


    }

    @Override
    public void updateShipment(Long shipmentId, ShipmentDTO shipmentDTO) {
        val sql = "UPDATE JuneCourierNetwork.shipment " +
                "SET shipment_type = :shipmentType, status = :status, departure_date = :departureDate, " +
                "arrival_date = :arrivalDate " +
                "WHERE id = :shipmentId AND was_deleted = FALSE;";

        val params = new MapSqlParameterSource();
        params.addValue("shipmentType", shipmentDTO.getType().name());
        params.addValue("status", shipmentDTO.getStatus().name());
        params.addValue("departureDate", shipmentDTO.getDepartureDate());
        params.addValue("arrivalDate", shipmentDTO.getArrivalDate());
        params.addValue("shipmentId", shipmentId);

        jdbcTemplate.update(sql, params);

    }

    @Override
    public void deleteShipment(Long shipmentId) {
        val sql = "UPDATE JuneCourierNetwork.shipment " +
                "SET was_deleted = TRUE " +
                "WHERE id = :shipmentId;";

        val params = new MapSqlParameterSource();
        params.addValue("shipmentId", shipmentId);

        jdbcTemplate.update(sql, params);
    }

    @Override
    public List<ShipmentDTO> getAllShipments() {
        val sql = "SELECT * FROM JuneCourierNetwork.shipment WHERE was_deleted = FALSE";

        return jdbcTemplate.query(sql, new ShipmentDTOMapper());
    }

    @Override
    public Optional<ShipmentDTO> findShipmentById(Long shipmentId) {
        val sql = "SELECT * FROM JuneCourierNetwork.shipment WHERE id = :shipmentId";

        val params = new MapSqlParameterSource();
        params.addValue("shipmentId", shipmentId);

        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, params, new ShipmentDTOMapper()));
    }

    @Override
    public void setShipmentType(Long shipmentId, ShipmentType shipmentType) {
        val sql = "UPDATE JuneCourierNetwork.shipment " +
                "SET shipment_type = :shipmentType " +
                "WHERE id = :shipmentId";

        val params = new MapSqlParameterSource();
        params.addValue("shipmentType", shipmentType.name());
        params.addValue("shipmentId", shipmentId);

        jdbcTemplate.update(sql, params);
    }

    @Override
    public void updateShipmentStatus(Long shipmentId, ShipmentStatus status) {
        val sql = "UPDATE JuneCourierNetwork.shipment " +
                "SET status = :status, updated_on = NOW() " +
                "WHERE id = :shipmentId";

        val params = new MapSqlParameterSource();
        params.addValue("status", status.name());
        params.addValue("shipmentId", shipmentId);

        jdbcTemplate.update(sql, params);
    }

    @Override
    public String getAirwayInvoiceFileName(Long shipmentId) {
        val sql = "SELECT airway_invoice FROM JuneCourierNetwork.shipment WHERE id = :shipmentId";

        val params = new MapSqlParameterSource();
        params.addValue("shipmentId", shipmentId);

        return jdbcTemplate.queryForObject(sql, params, String.class);
    }

    @Override
    public void updateShipmentManifestFileName(String fileName, Long shipmentId) {
        val sql = "UPDATE JuneCourierNetwork.shipment " +
                "SET shipment_manifest = :fileName " +
                "WHERE id = :shipmentId";

        val params = new MapSqlParameterSource();
        params.addValue("fileName", fileName);
        params.addValue("shipmentId", shipmentId);

        jdbcTemplate.update(sql, params);
    }

    @Override
    public void updateAirwayInvoiceFileName(String fileName, Long shipmentId) {
        val sql = "UPDATE JuneCourierNetwork.shipment " +
                "SET airway_invoice = :fileName " +
                "WHERE id = :shipmentId";

        val params = new MapSqlParameterSource();
        params.addValue("fileName", fileName);
        params.addValue("shipmentId", shipmentId);

        jdbcTemplate.update(sql, params);
    }


}
