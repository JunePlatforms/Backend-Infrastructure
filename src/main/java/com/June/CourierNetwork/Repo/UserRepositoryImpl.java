package com.June.CourierNetwork.Repo;

import com.June.CourierNetwork.Enum.Role;
import com.June.CourierNetwork.Mapper.UserMapper;
import com.June.CourierNetwork.Model.User;
import com.June.CourierNetwork.Repo.Contract.CourierRepository;
import com.June.CourierNetwork.Repo.Contract.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Long save(User user) {
        val sql = "INSERT INTO JuneCourierNetwork.`user` " +
                "(first_name, last_name, email_address, password, phone_number, role, is_verified, is_active) " +
                "VALUES(:firstName, :lastName, :emailAddress, :password, :phoneNumber, :role, 0, 1)";

        val params = new MapSqlParameterSource();

        params.addValue("firstName", user.getFirstName());
        params.addValue("lastName", user.getLastName());
        params.addValue("emailAddress", user.getEmailAddress());
        params.addValue("password", user.getPassword());
        params.addValue("phoneNumber", user.getPhoneNumber());
        params.addValue("role", user.getRole().name());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(sql, params, keyHolder, new String[]{"id"});

        return keyHolder.getKey().longValue();
    }

    @Override
    public Long updateUser(Long id, User user) {

        return null;
    }

    @Override
    public Optional<User> findUserByEmail(String email) {

        val sql = "SELECT * FROM JuneCourierNetwork.user WHERE email_address = :email";

        val params = new MapSqlParameterSource();

        params.addValue("email", email);

        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, params, new UserMapper()));
    }
}
