package com.June.CourierNetwork.Mapper;

import com.June.CourierNetwork.Enum.TokenType;
import com.June.CourierNetwork.Model.Token;
import com.June.CourierNetwork.Model.UpdatePasswordRequest;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class passwordMapper implements RowMapper<UpdatePasswordRequest> {
    @Override
    public UpdatePasswordRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
        return UpdatePasswordRequest.builder()
                .oldPassword(rs.getString("old_password"))
                .build();
    }
}
