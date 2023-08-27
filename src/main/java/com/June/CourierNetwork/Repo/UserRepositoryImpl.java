package com.June.CourierNetwork.Repo;

import com.June.CourierNetwork.Mapper.UserMapper;
import com.June.CourierNetwork.Model.UpdatePasswordRequest;
import com.June.CourierNetwork.Model.UpdateUserRequest;
import com.June.CourierNetwork.Model.User;
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
    public void deleteById(Long userId) {
        val sql = "UPDATE JuneCourierNetwork.`user` SET is_active = 0 WHERE id = :userId";

        val params = new MapSqlParameterSource();
        params.addValue("userId", userId);
        jdbcTemplate.update(sql, params);
    }

    @Override
    public Optional<User> findActiveUserByEmail(String email) {
        val sql = "SELECT * FROM JuneCourierNetwork.user WHERE email_address = :email AND is_active = 1";

        val params = new MapSqlParameterSource();

        params.addValue("email", email);

        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, params, new UserMapper()));
    }

    @Override
    public int updateUserPassword(Long id, UpdatePasswordRequest passwordRequest) {
        val sql = "UPDATE JuneCourierNetwork.`user` SET password = :password WHERE id = :userId";

        val params = new MapSqlParameterSource();
        params.addValue("password", passwordRequest.getNewPassword());
        params.addValue("userId", id);

        return jdbcTemplate.update(sql, params);
    }

    @Override
    public Optional<UpdatePasswordRequest> getUserPassword(Long id) {
        val sql = "SELECT password FROM JuneCourierNetwork.user WHERE id = :userId";

        val params = new MapSqlParameterSource();
        params.addValue("userId", id);

        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, params, UpdatePasswordRequest.class));
    }

    @Override
    public int updateUser(Long id, UpdateUserRequest userDetails) {
        val sql = "UPDATE JuneCourierNetwork.`user` SET " +
                "first_name = :firstName, last_name = :lastName, email_address = :emailAddress, " +
                "phone_number = :phoneNumber WHERE id = :userId";

        val params = new MapSqlParameterSource();
        params.addValue("firstName", userDetails.getFirstname());
        params.addValue("lastName", userDetails.getLastname());
        params.addValue("emailAddress", userDetails.getEmail());
        params.addValue("phoneNumber", userDetails.getPhoneNumber());
        params.addValue("userId", id);

        return jdbcTemplate.update(sql, params);}

    @Override
    public Optional<User> findUserByEmail(String email) {

        val sql = "SELECT * FROM JuneCourierNetwork.user WHERE email_address = :email";

        val params = new MapSqlParameterSource();

        params.addValue("email", email);

        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, params, new UserMapper()));
    }

    @Override
    public Optional<User> findUserById(Long userId) {
        val sql = "SELECT * FROM JuneCourierNetwork.user WHERE id = :userId";

        val params = new MapSqlParameterSource();

        params.addValue("userId", userId);

        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, params, new UserMapper()));
    }
}
