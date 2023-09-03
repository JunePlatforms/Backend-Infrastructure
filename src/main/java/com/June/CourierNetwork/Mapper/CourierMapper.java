package com.June.CourierNetwork.Mapper;


import com.June.CourierNetwork.Model.Courier;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CourierMapper implements RowMapper<Courier> {
    @Override
    public Courier mapRow(ResultSet rs, int rowNum) throws SQLException {

        return Courier.builder()
                .courierId((long) rs.getInt("id"))
                .acceptedTermsAndConditions(rs.getBoolean("accepted_terms_and_conditions"))
                .assessmentScore(rs.getInt("assessment_score"))
                .rating(rs.getInt("rating"))
                .isAvailable(rs.getBoolean("is_available"))
                .build();
    }
}
