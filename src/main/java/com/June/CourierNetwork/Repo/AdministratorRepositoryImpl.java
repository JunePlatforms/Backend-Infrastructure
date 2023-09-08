package com.June.CourierNetwork.Repo;

import com.June.CourierNetwork.Mapper.AdministratorMapper;
import com.June.CourierNetwork.Model.Administrator;
import com.June.CourierNetwork.Repo.Contract.AdministratorRepository;
import com.June.CourierNetwork.Repo.Contract.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AdministratorRepositoryImpl implements AdministratorRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;


    @Override
    public Optional<Administrator> findByUserId(long userId) {
        val sql = "SELECT * FROM JuneCourierNetwork.admin_user " +
                "WHERE user_id = :user_id";

        val params = new MapSqlParameterSource();

        params.addValue("user_id", userId);

        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, params, new AdministratorMapper()));
    }

    @Override
    public Long save(Administrator administrator) {
        long userId;

        val sql = "INSERT INTO JuneCourierNetwork.admin_user " +
                "(user_id) " +
                "VALUES(:userId)";

        try {
            userId = userRepository.save(administrator.getUser());
        }catch (Exception e){
            return null;
        }

        val warehouseClerkParams = new MapSqlParameterSource();
        warehouseClerkParams.addValue("userId", userId);

        jdbcTemplate.update(sql, warehouseClerkParams);

        return userId;
    }
}
