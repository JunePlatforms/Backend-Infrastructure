package com.June.CourierNetwork.Mapper;


import com.June.CourierNetwork.Enum.Role;
import com.June.CourierNetwork.Model.Courier;
import com.June.CourierNetwork.Model.Customer;
import com.June.CourierNetwork.Model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
public class CustomerMapper implements  RowMapper<Customer>{

    @Override
    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {

        return Customer.builder()
                .id((long) rs.getInt("id"))
                .profilePic(rs.getString("profilePic"))
//                .viewInvoice(rs.get"PDF Invoice"))
                .build();
    }
}
