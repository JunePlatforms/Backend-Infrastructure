package com.June.CourierNetwork.Repo;

import com.June.CourierNetwork.Mapper.CourierMapper;
import com.June.CourierNetwork.Model.Courier;
import com.June.CourierNetwork.Repo.Contract.CourierRepository;
import com.June.CourierNetwork.Repo.Contract.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CourierRepositoryImpl implements CourierRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;


    @Override
    public Long updateRating(Long userId, Courier courier) {
        val updateSql = "UPDATE JuneCourierNetwork.courier_user " +
                "SET rating = :rating, " +
                "WHERE user_id = :userId";

        val params = new MapSqlParameterSource();
        params.addValue("userId", userId);
        params.addValue("rating", courier.getRating());

        jdbcTemplate.update(updateSql, params);

        return userId;
    }

    @Override
    public Long updateAvailability(Long userId, Courier courier) {
        val updateSql = "UPDATE JuneCourierNetwork.courier_user " +
                "SET is_available = :isAvailable " +
                "WHERE user_id = :userId";

        val params = new MapSqlParameterSource();
        params.addValue("userId", userId);
        params.addValue("isAvailable", courier.getIsAvailable());

        jdbcTemplate.update(updateSql, params);

        return userId;
    }

    @Override
    public Optional<Courier> findByUserId(long userId) {
        val sql = "SELECT * FROM JuneCourierNetwork.courier_user " +
                "WHERE user_id = :user_id";

        val params = new MapSqlParameterSource();

        params.addValue("user_id", userId);

        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, params, new CourierMapper()));
    }

    @Override
    public Long save(Courier courier) {
        long userId;

        val sql = "INSERT INTO JuneCourierNetwork.courier_user " +
                "(accepted_terms_and_conditions, assessment_score, rating, is_available, user_id) " +
                "VALUES(:acceptedTermsAndConditions, :assessmentScore, 0, 1, :userId);";

        try {
            userId = userRepository.save(courier.getUser());
        }catch (Exception e){
            return null;
        }

        val courierParams = new MapSqlParameterSource();
        courierParams.addValue("acceptedTermsAndConditions", courier.getAcceptedTermsAndConditions());
        courierParams.addValue("assessmentScore", courier.getAssessmentScore());
        courierParams.addValue("userId", userId);

        jdbcTemplate.update(sql, courierParams);

        return userId;
    }
}
