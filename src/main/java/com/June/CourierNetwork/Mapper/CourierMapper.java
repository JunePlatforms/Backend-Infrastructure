package com.June.CourierNetwork.Mapper;


import com.June.CourierNetwork.Enum.Role;
import com.June.CourierNetwork.Model.Courier;
import com.June.CourierNetwork.Model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CourierMapper implements RowMapper<Courier> {
    @Override
    public Courier mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = User.builder()
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .emailAddress(rs.getString("email_address"))
                .password(rs.getString("password"))
                .phoneNumber(rs.getString("phone_number"))
                .role(Role.valueOf(rs.getString("role")))
                .isVerified(rs.getBoolean("is_verified"))
                .isActive(rs.getBoolean("is_active"))
                .build();

        return Courier.builder()
                .id((long) rs.getInt("id"))
                .acceptedTermsAndConditions(rs.getBoolean("accepted_terms_and_conditions"))
                .assessmentScore(rs.getInt("assessment_score"))
                .rating(rs.getInt("rating"))
                .isAvailable(rs.getBoolean("is_available"))
                .user(user)
                .build();
    }
}
