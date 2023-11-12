package com.June.CourierNetwork.Repo.Contract;

import com.June.CourierNetwork.Model.Token;

import java.util.List;
import java.util.Optional;

public interface TokenRepository {

    List<Token> findAllValidTokenByUser(Integer id);

    Optional<Token> findByToken(String token);


    void save(Token token);

    void saveAll(List<Token> validUserTokens);

    void deleteAllExpiredTokens();

    void deleteRevokedToken(Integer id);

    void revokeAllActiveUserTokens(Integer id);

}
