package com.June.CourierNetwork.Service.CronJobs;

import com.June.CourierNetwork.Repo.Contract.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenCleanUpTask {
    private final TokenRepository tokenRepository;
    @Scheduled(cron = "0 0 * * *") // Cron expression for daily execution at midnight
    public void cleanupExpiredTokens() {
        tokenRepository.deleteAllRevokedTokens();
    }
}
