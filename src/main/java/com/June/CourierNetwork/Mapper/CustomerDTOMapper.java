package com.June.CourierNetwork.Mapper;


import com.June.CourierNetwork.DTO.CustomerDTO;
import com.June.CourierNetwork.Model.Customer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDTOMapper implements RowMapper<CustomerDTO> {
    @Override
    public CustomerDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

        return CustomerDTO.builder()
                .id((long) rs.getInt("id"))
                .username(rs.getString("username"))
                .customerNumber(rs.getString("customer_number"))
                .profilePicture(rs.getString("profile_image"))
                .acceptedTermsAndConditions(rs.getBoolean("accepted_terms_and_conditions"))
                .build();
    }
}
