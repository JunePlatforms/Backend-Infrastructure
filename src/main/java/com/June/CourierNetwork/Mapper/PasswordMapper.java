package com.June.CourierNetwork.Mapper;

import com.June.CourierNetwork.Model.UpdatePasswordRequest;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PasswordMapper implements RowMapper<UpdatePasswordRequest> {
    @Override
    public UpdatePasswordRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
        return UpdatePasswordRequest.builder()
                .oldPassword(rs.getString("password"))
                .build();
    }
}
