package com.June.CourierNetwork.Repo;

import com.June.CourierNetwork.Mapper.ProductDetailsMapper;
import com.June.CourierNetwork.Mapper.ShippingLabelMapper;
import com.June.CourierNetwork.Mapper.WarehouseClerkMapperMapper;
import com.June.CourierNetwork.Model.ProductDetails;
import com.June.CourierNetwork.Model.ProductDetailsRequest;
import com.June.CourierNetwork.Model.ShippingLabel;
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
    public ShippingLabel generateShippingLabel(Long productId) {
        val sql = "SELECT u.first_name, u.last_name, cpd.weight, cpd.description, cu.customer_number " +
                "FROM JuneCourierNetwork.user u " +
                "JOIN JuneCourierNetwork.customer_product_details cpd ON u.id = cpd.user_id " +
                "JOIN JuneCourierNetwork.customer_user cu ON u.id = cu.user_id " +
                "WHERE cpd.id = :productId;";

        val params = new MapSqlParameterSource();
        params.addValue("productId", productId);

        return jdbcTemplate.queryForObject(sql, params, new ShippingLabelMapper());
    }
}
