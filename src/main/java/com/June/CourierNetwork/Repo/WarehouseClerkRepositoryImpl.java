package com.June.CourierNetwork.Repo;

import com.June.CourierNetwork.Mapper.CourierMapper;
import com.June.CourierNetwork.Mapper.WarehouseClerkMapperMapper;
import com.June.CourierNetwork.Model.Courier;
import com.June.CourierNetwork.Model.WarehouseClerk;
import com.June.CourierNetwork.Repo.Contract.UserRepository;
import com.June.CourierNetwork.Repo.Contract.WarehouseClerkRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

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
}
