package com.June.CourierNetwork.Repo;

import com.June.CourierNetwork.Mapper.ProductDetailsMapper;
import com.June.CourierNetwork.Mapper.WarehouseClerkMapperMapper;
import com.June.CourierNetwork.Model.ProductDetails;
import com.June.CourierNetwork.Model.ProductDetailsRequest;
import com.June.CourierNetwork.Model.WarehouseClerk;
import com.June.CourierNetwork.Repo.Contract.UserRepository;
import com.June.CourierNetwork.Repo.Contract.WarehouseClerkRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class WarehouseClerkRepositoryImpl implements WarehouseClerkRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;


    @Override
    public Optional<WarehouseClerk> findByUserId(long userId) {
        val sql = "SELECT * FROM JuneCourierNetwork.warehouse_clerk_user " +
                "WHERE user_id = :user_id";

        val params = new MapSqlParameterSource();

        params.addValue("user_id", userId);

        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, params, new WarehouseClerkMapperMapper()));
    }

    @Override
    public Long save(WarehouseClerk warehouseClerk) {
        long userId;

        val sql = "INSERT INTO JuneCourierNetwork.warehouse_clerk_user " +
                "(user_id) " +
                "VALUES(:userId)";

        try {
            userId = userRepository.save(warehouseClerk.getUser());
        }catch (Exception e){
            return null;
        }

        val warehouseClerkParams = new MapSqlParameterSource();
        warehouseClerkParams.addValue("userId", userId);

        jdbcTemplate.update(sql, warehouseClerkParams);

        return userId;
    }

    @Override
    public void createProduct(ProductDetailsRequest productDetailsRequest) {
        val sql = "INSERT INTO JuneCourierNetwork.customer_product_details " +
                "(product_weight, description, supplier_name, tracking_number, was_deleted, user_id) " +
                "VALUES(:weight, :description, :supplierName, :trackingNumber, 0, :userId);";

        val params = new MapSqlParameterSource();
        params.addValue("weight", productDetailsRequest.getWeight());
        params.addValue("description", productDetailsRequest.getDescription());
        params.addValue("supplierName", productDetailsRequest.getSupplierName());
        params.addValue("trackingNumber", productDetailsRequest.getTrackingNumber());
        params.addValue("userId", productDetailsRequest.getUserId());

        jdbcTemplate.update(sql, params);
    }

    @Override
    public void updateProduct(Long productId, ProductDetailsRequest productDetailsRequest) {
        val sql = "UPDATE JuneCourierNetwork.customer_product_details " +
                "SET product_weight = :weight, description = :description, " +
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
        val sql = "SELECT * FROM JuneCourierNetwork.customer_product_details " +
                "WHERE user_id = :userId";

        val params = new MapSqlParameterSource();
        params.addValue("userId", userId);

        return jdbcTemplate.query(sql, params, new ProductDetailsMapper());
    }

    @Override
    public List<ProductDetails> getAllProducts() {
        val sql = "SELECT * FROM JuneCourierNetwork.customer_product_details";

        return jdbcTemplate.query(sql, new ProductDetailsMapper());
    }
}
