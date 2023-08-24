package com.June.CourierNetwork.Mapper;

import com.June.CourierNetwork.Enum.TokenType;
import com.June.CourierNetwork.Model.Token;
import com.June.CourierNetwork.Model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TokenMapper implements RowMapper<Token> {
    @Override
    public Token mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Token.builder()
                .id((long) rs.getInt("id"))
                .token(rs.getString("token"))
                .tokenType(TokenType.valueOf(rs.getString("token_type")))
                .expired(rs.getBoolean("expired"))
                .revoked(rs.getBoolean("revoked"))
                .userId((long) rs.getInt("user_id"))
                .build();
    }
}
