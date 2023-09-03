package com.June.CourierNetwork.Mapper;

import com.June.CourierNetwork.Model.ShippingLabel;
import com.June.CourierNetwork.Model.UpdatePasswordRequest;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ShippingLabelMapper implements RowMapper<ShippingLabel> {
    @Override
    public ShippingLabel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return ShippingLabel.builder()
                .customerNumber(rs.getString("customer_number"))
                .description(rs.getString("description"))
                .weight(rs.getString("weight"))
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .build();
    }
}
