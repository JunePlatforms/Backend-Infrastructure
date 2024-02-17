package com.June.CourierNetwork.Repo;

import com.June.CourierNetwork.DTO.ProductDetailsDTO;
import com.June.CourierNetwork.Enum.PackageStatus;
import com.June.CourierNetwork.Enum.ShipmentType;
import com.June.CourierNetwork.Mapper.ProductDetailsDTOMapper;
import com.June.CourierNetwork.Model.ProductDetailsRequest;
import com.June.CourierNetwork.Repo.Contract.ProductRepository;
import com.June.CourierNetwork.Repo.Contract.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;

    @Override
    public Long createProduct(ProductDetailsRequest productDetailsRequest) {
        val sql = "INSERT INTO JuneCourierNetwork.customer_product_details " +
                "(weight, shipment_type, status, description, supplier_name, tracking_number, was_deleted, user_id, " +
                "created_on) " +
                "VALUES(:weight, :shipmentType, :status, :description, :supplierName, :trackingNumber, 0, :userId, " +
                ":createdOn);";

        val params = new MapSqlParameterSource();
        params.addValue("weight", productDetailsRequest.getWeight());
        params.addValue("shipmentType", ShipmentType.NULL.name());
        params.addValue("status", PackageStatus.CREATED.name());
        params.addValue("description", productDetailsRequest.getDescription());
        params.addValue("supplierName", productDetailsRequest.getSupplierName());
        params.addValue("trackingNumber", productDetailsRequest.getTrackingNumber());
        params.addValue("userId", productDetailsRequest.getUserId());
        params.addValue("createdOn", java.time.LocalDateTime.now());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(sql, params, keyHolder, new String[]{"id"});

        long id = keyHolder.getKey().longValue();

        createJunId(id);

        return id;
    }

    private void createJunId(long id)   {
        val sql = "UPDATE JuneCourierNetwork.customer_product_details " +
                "SET jun_id = :junId " +
                "WHERE id = :id";

        val params = new MapSqlParameterSource();

        params.addValue("junId", "JUN" + id);
        params.addValue("id", id);

        jdbcTemplate.update(sql, params);
    }

    @Override
    public void updateProduct(Long productId, ProductDetailsRequest productDetailsRequest) {
        val sql = "UPDATE JuneCourierNetwork.customer_product_details " +
                "SET weight = :weight, description = :description, " +
                "supplier_name = :supplierName, tracking_number = :trackingNumber " +
                "WHERE id = :productId";

        val params = new MapSqlParameterSource();
        params.addValue("weight", productDetailsRequest.getWeight());
        params.addValue("description", productDetailsRequest.getDescription());
        params.addValue("supplierName", productDetailsRequest.getSupplierName());
        params.addValue("trackingNumber", productDetailsRequest.getTrackingNumber());
        params.addValue("productId", productId);

        jdbcTemplate.update(sql, params);

    }

    @Override
    public void deleteProduct(Long productId) {
        val sql = "UPDATE JuneCourierNetwork.customer_product_details " +
                "SET was_deleted = 1 " +
                "WHERE id = :productId";

        val params = new MapSqlParameterSource();
        params.addValue("productId", productId);

        jdbcTemplate.update(sql, params);

    }

    @Override
    public List<ProductDetailsDTO> findProductsByUserId(Long userId) {
        val sql = "SELECT cpd.*, cu.customer_number FROM JuneCourierNetwork.customer_product_details cpd " +
                "JOIN JuneCourierNetwork.customer_user cu ON cpd.user_id = cu.user_id " +
                "WHERE cpd.user_id = :userId AND cpd.was_deleted = false";

        val params = new MapSqlParameterSource();
        params.addValue("userId", userId);

        return jdbcTemplate.query(sql, params, new ProductDetailsDTOMapper());
    }

    @Override
    public List<ProductDetailsDTO> getAllProducts() {
        val sql = "SELECT cpd.*, cu.customer_number, u.first_name, u.last_name " +
                "FROM JuneCourierNetwork.customer_product_details cpd " +
                "JOIN JuneCourierNetwork.customer_user cu ON cpd.user_id = cu.user_id " +
                "JOIN JuneCourierNetwork.user u ON cpd.user_id = u.id " +
                "WHERE cpd.was_deleted = false";

        return jdbcTemplate.query(sql, new ProductDetailsDTOMapper());
    }

    @Override
    public Optional<ProductDetailsDTO> findProductById(Long packageId) {
        val sql = "SELECT cpd.*, cu.customer_number FROM JuneCourierNetwork.customer_product_details cpd " +
                "JOIN JuneCourierNetwork.customer_user cu ON cpd.user_id = cu.user_id " +
                "WHERE cpd.id = :packageId";

        val params = new MapSqlParameterSource();
        params.addValue("packageId", packageId);

        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, params, new ProductDetailsDTOMapper()));
    }

    @Override
    public void setShipmentType(Long packageId, ShipmentType shipmentType) {
        val sql = "UPDATE JuneCourierNetwork.customer_product_details " +
                "SET shipment_type = :shipmentType " +
                "WHERE id = :packageId";

        val params = new MapSqlParameterSource();
        params.addValue("packageId", packageId);
        params.addValue("shipmentType", shipmentType.name());

        jdbcTemplate.update(sql, params);
    }

    @Override
    public void updateProductStatus(Long productId, PackageStatus status) {
        val sql = "UPDATE JuneCourierNetwork.customer_product_details " +
                "SET status = :status " +
                "WHERE id = :productId";

        val params = new MapSqlParameterSource();
        params.addValue("productId", productId);
        params.addValue("status", status.name());

        jdbcTemplate.update(sql, params);
    }

    @Override
    public String getPreAlert(Long productId) {
        val sql = "SELECT pre_alert FROM JuneCourierNetwork.customer_product_details " +
                "WHERE id = :productId";

        val params = new MapSqlParameterSource();
        params.addValue("productId", productId);

        return jdbcTemplate.queryForObject(sql, params, String.class);
    }

    @Override
    public void uploadPreAlert(String newFileName, Long productId) {
        val sql = "UPDATE JuneCourierNetwork.customer_product_details " +
                "SET pre_alert = :newFileName " +
                "WHERE id = :productId";

        val params = new MapSqlParameterSource();
        params.addValue("productId", productId);
        params.addValue("newFileName", newFileName);

        jdbcTemplate.update(sql, params);
    }

    @Override
    public void addProductsToShipment(List<Long> productIds, Long shipmentId) {
        val sql = "UPDATE JuneCourierNetwork.customer_product_details " +
                "SET shipment_id = :shipmentId " +
                "WHERE id IN (:productIds)";

        val params = new MapSqlParameterSource();
        params.addValue("productIds", productIds);
        params.addValue("shipmentId", shipmentId);

        jdbcTemplate.update(sql, params);
    }

    @Override
    public List<ProductDetailsDTO> findProductDetailsByShipmentId(Long shipmentId) {
        val sql = "SELECT cpd.*, cu.customer_number FROM JuneCourierNetwork.customer_product_details cpd " +
                "JOIN JuneCourierNetwork.customer_user cu ON cpd.user_id = cu.user_id " +
                "WHERE cpd.shipment_id = :shipmentId";

        val params = new MapSqlParameterSource();
        params.addValue("shipmentId", shipmentId);

        return jdbcTemplate.query(sql, params, new ProductDetailsDTOMapper());
    }

    @Override
    public Long findProductOwnerIdByProductId(Long productId) {
        val sql = "SELECT user_id FROM JuneCourierNetwork.customer_product_details " +
                "WHERE id = :productId";

        val params = new MapSqlParameterSource();
        params.addValue("productId", productId);

        return jdbcTemplate.queryForObject(sql, params, Long.class);
    }

    @Override
    public BigDecimal calculateTotalSpent(List<Long> productIds) {
        val sql = "SELECT SUM(shipping_fee) FROM JuneCourierNetwork.customer_product_details " +
                "WHERE id IN (:productIds)";

        val params = new MapSqlParameterSource();
        params.addValue("productIds", productIds);

        return jdbcTemplate.queryForObject(sql, params, BigDecimal.class);

    }
}
