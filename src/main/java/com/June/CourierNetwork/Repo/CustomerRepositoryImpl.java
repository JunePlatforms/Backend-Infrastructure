package com.June.CourierNetwork.Repo;

import com.June.CourierNetwork.DTO.CustomerDTO;
import com.June.CourierNetwork.Mapper.CustomerDTOMapper;
import com.June.CourierNetwork.Model.Customer;
import com.June.CourierNetwork.Repo.Contract.CustomerRepository;
import com.June.CourierNetwork.Repo.Contract.UserRepository;
import com.June.CourierNetwork.Service.Contract.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    @Override
    public Long save(Customer customer, Long userId) {
        String defaultProfileImage = "default.png";

        val sql = "INSERT INTO JuneCourierNetwork.customer_user " +
                "(username, customer_number, mail_box, profile_image, accepted_terms_and_conditions, user_id) " +
                "VALUES(:username, :customerNumber, :mailBox, :profileImage, :acceptedTermsAndConditions, :userId);";



        val courierParams = new MapSqlParameterSource();
        courierParams.addValue("customerNumber", customer.getCustomerNumber());
        courierParams.addValue("mailBox", customer.getMailBox());
        courierParams.addValue("username", customer.getUsername());
        courierParams.addValue("acceptedTermsAndConditions", customer.getAcceptedTermsAndConditions());
        courierParams.addValue("profileImage", defaultProfileImage);
        courierParams.addValue("userId", userId);

        jdbcTemplate.update(sql, courierParams);

        return userId;
    }

    @Override
    public int updateProfileImage(Long userId, String fileName) {
        val sql = "UPDATE JuneCourierNetwork.customer_user " +
                "SET profile_image = :profileImage " +
                "WHERE user_id = :userId";


        val params = new MapSqlParameterSource();
        params.addValue("profileImage", fileName);
        params.addValue("userId", userId);

        return jdbcTemplate.update(sql, params);
    }

    @Override
    public String getProfileImage(Long userId){
        val sql = "SELECT profile_image FROM JuneCourierNetwork.customer_user WHERE user_id = :userId";

        val params = new MapSqlParameterSource();
        params.addValue("userId", userId);

        return jdbcTemplate.queryForObject(sql, params, String.class);
    }

    @Override
    public Optional<CustomerDTO> findByUserId(Long userId) {
        val sql = "SELECT * FROM JuneCourierNetwork.customer_user WHERE user_id = :userId";

        val params = new MapSqlParameterSource();
        params.addValue("userId", userId);

        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, params, new CustomerDTOMapper()));
    }

    @Override
    public List<CustomerDTO> findAll() {
        val sql = "SELECT * FROM JuneCourierNetwork.customer_user " +
                "JOIN JuneCourierNetwork.user ON " +
                "JuneCourierNetwork.user.id = JuneCourierNetwork.customer_user.user_id " +
                "WHERE JuneCourierNetwork.user.is_active = true;";

        return jdbcTemplate.query(sql, new CustomerDTOMapper());
    }


}
