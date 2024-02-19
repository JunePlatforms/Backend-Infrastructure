package com.June.CourierNetwork.Mapper;


import com.June.CourierNetwork.Enum.Role;
import com.June.CourierNetwork.Model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return User.builder()
                .id((long) rs.getInt("id"))
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .emailAddress(rs.getString("email_address"))
                .password(rs.getString("password"))
                .phoneNumber(rs.getString("phone_number"))
                .role(Role.valueOf(rs.getString("role")))
                .isVerified(rs.getBoolean("is_verified"))
                .isActive(rs.getBoolean("is_active"))
                .createdOn(rs.getTimestamp("created_on"))
                .build();
    }
}
