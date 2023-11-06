package com.June.CourierNetwork.Service.CronJobs;

import com.June.CourierNetwork.Repo.Contract.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenCleanUpTask {
    private final TokenRepository tokenRepository;
    private static final Logger logger = LoggerFactory.getLogger(TokenCleanUpTask.class);

    @Scheduled(cron = "0 */30 * * * *")
    public void cleanupExpiredTokens() {
        tokenRepository.deleteAllExpiredTokens();
        logger.info("Token cleanup initiated successfully.");
    }
}
