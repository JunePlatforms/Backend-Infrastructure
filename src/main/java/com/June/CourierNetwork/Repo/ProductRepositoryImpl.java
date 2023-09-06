package com.June.CourierNetwork.Repo;

import com.June.CourierNetwork.Enum.PackageStatus;
import com.June.CourierNetwork.Enum.ShipmentType;
import com.June.CourierNetwork.Mapper.ProductDetailsMapper;
import com.June.CourierNetwork.Model.ProductDetails;
import com.June.CourierNetwork.Model.ProductDetailsRequest;
import com.June.CourierNetwork.Repo.Contract.ProductRepository;
import com.June.CourierNetwork.Repo.Contract.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;

    @Override
    public void createProduct(ProductDetailsRequest productDetailsRequest) {
        val sql = "INSERT INTO JuneCourierNetwork.customer_product_details " +
                "(weight, shipment_type, status, description, supplier_name, tracking_number, was_deleted, user_id) " +
                "VALUES(:weight, :shipmentType, :status, :description, :supplierName, :trackingNumber, 0, :userId);";

        val params = new MapSqlParameterSource();
        params.addValue("weight", productDetailsRequest.getWeight());
        params.addValue("shipmentType", ShipmentType.NULL.name());
        params.addValue("status", PackageStatus.CREATED.name());
        params.addValue("description", productDetailsRequest.getDescription());
        params.addValue("supplierName", productDetailsRequest.getSupplierName());
        params.addValue("trackingNumber", productDetailsRequest.getTrackingNumber());
        params.addValue("userId", productDetailsRequest.getUserId());

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
    public List<ProductDetails> findProductsByUserId(Long userId) {
        val sql = "SELECT cpd.*, cu.customer_number FROM JuneCourierNetwork.customer_product_details cpd " +
                "JOIN JuneCourierNetwork.customer_user cu ON cpd.user_id = cu.user_id " +
                "WHERE cpd.user_id = :userId AND cpd.was_deleted = false";

        val params = new MapSqlParameterSource();
        params.addValue("userId", userId);

        return jdbcTemplate.query(sql, params, new ProductDetailsMapper());
    }

    @Override
    public List<ProductDetails> getAllProducts() {
        val sql = "SELECT cpd.*, cu.customer_number FROM JuneCourierNetwork.customer_product_details cpd " +
                "JOIN JuneCourierNetwork.customer_user cu ON cpd.user_id = cu.user_id WHERE cpd.was_deleted = false";

        return jdbcTemplate.query(sql, new ProductDetailsMapper());
    }

    @Override
    public Optional<ProductDetails> findProductById(Long packageId) {
        val sql = "SELECT cpd.*, cu.customer_number FROM JuneCourierNetwork.customer_product_details cpd " +
                "JOIN JuneCourierNetwork.customer_user cu ON cpd.user_id = cu.user_id " +
                "WHERE cpd.id = :packageId";

        val params = new MapSqlParameterSource();
        params.addValue("packageId", packageId);

        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, params, new ProductDetailsMapper()));
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
}
