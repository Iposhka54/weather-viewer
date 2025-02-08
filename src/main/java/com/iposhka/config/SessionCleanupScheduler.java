package com.iposhka.config;

import com.iposhka.repository.SessionRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SessionCleanupScheduler {
    private final SessionRepository sessionRepository;
    private final Logger log;

    public SessionCleanupScheduler(SessionRepository sessionRepository, Logger log) {
        this.sessionRepository = sessionRepository;
        this.log = log;
    }

    @Scheduled(fixedRate = 15000)
    @Transactional
    public void cleanExpiredSessions(){
        log.debug("Cleanup expired sessions from database");
        int countDeletedSessions = sessionRepository.deleteExpiredSessions();
        log.debug("Deleted " + countDeletedSessions + " expired sessions from database");
    }
}
