package com.June.CourierNetwork.Repo;

import com.June.CourierNetwork.Mapper.TokenMapper;
import com.June.CourierNetwork.Model.Token;
import com.June.CourierNetwork.Repo.Contract.TokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TokenRepositoryImpl implements TokenRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;


    @Override
    public List<Token> findAllValidTokenByUser(Integer user_id) {
        val sql = "SELECT * FROM JuneCourierNetwork.token WHERE user_id = :user_id " +
                "AND revoked = 0 AND expired = 0";

        val params = new MapSqlParameterSource();

        params.addValue("user_id", user_id);

        return jdbcTemplate.query(sql, params, new TokenMapper());
    }

    @Override
    public Optional<Token> findByToken(String token) {
        val sql = "SELECT * FROM JuneCourierNetwork.token WHERE token = :token";

        val params = new MapSqlParameterSource();

        params.addValue("token", token);

        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, params, new TokenMapper()));
    }

    @Override
    public void save(Token token) {
        val sql = "INSERT INTO JuneCourierNetwork.token " +
                "(token, token_type, revoked, expired, user_id) " +
                "VALUES(:token, :token_type, :revoked, :expired, :user_id)";


        val params = new MapSqlParameterSource();

        params.addValue("token", token.getToken());
        params.addValue("token_type", token.getTokenType().name());
        params.addValue("revoked", token.isRevoked());
        params.addValue("expired", token.isExpired());
        params.addValue("user_id", token.getUserId());

        jdbcTemplate.update(sql, params);
    }

    @Override
    public void saveAll(List<Token> validUserTokens) {
        for (Token token : validUserTokens) {
            save(token);
        }
    }

    @Override
    public void deleteAllExpiredTokens() {
        val sql = "DELETE FROM JuneCourierNetwork.token WHERE expired = true";

        val params = new MapSqlParameterSource();

        jdbcTemplate.update(sql, params);
    }

    @Override
    public void deleteRevokedToken(Integer id) {
        val sql = "DELETE FROM JuneCourierNetwork.token WHERE user_id = :id AND revoked = true";

        val params = new MapSqlParameterSource();

        params.addValue("id", id);

        jdbcTemplate.update(sql, params);
    }
}
