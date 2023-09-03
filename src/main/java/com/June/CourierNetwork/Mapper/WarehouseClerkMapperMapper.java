package com.June.CourierNetwork.Mapper;

import com.June.CourierNetwork.Model.WarehouseClerk;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WarehouseClerkMapperMapper implements RowMapper<WarehouseClerk> {
    @Override
    public WarehouseClerk mapRow(ResultSet rs, int rowNum) throws SQLException {
        return WarehouseClerk.builder()
                .warehouseClerkId((long) rs.getInt("id"))
                .build();
    }
}
