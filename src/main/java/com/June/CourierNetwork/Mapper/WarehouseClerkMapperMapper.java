package com.June.CourierNetwork.Mapper;

import com.June.CourierNetwork.Enum.TokenType;
import com.June.CourierNetwork.Model.Token;
import com.June.CourierNetwork.Model.WarehouseClerk;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WarehouseClerkMapperMapper implements RowMapper<WarehouseClerk> {
    @Override
    public WarehouseClerk mapRow(ResultSet rs, int rowNum) throws SQLException {
        return WarehouseClerk.builder()
                .id((long) rs.getInt("id"))
                .build();
    }
}
