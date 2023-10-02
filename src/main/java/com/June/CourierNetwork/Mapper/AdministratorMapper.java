package com.June.CourierNetwork.Mapper;

import com.June.CourierNetwork.Model.Administrator;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdministratorMapper implements RowMapper<Administrator> {
    @Override
    public Administrator mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Administrator.builder()
                .adminId((long) rs.getInt("id"))
                .build();
    }
}
