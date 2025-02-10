package com.iposhka.config;

import com.iposhka.model.Session;
import com.iposhka.model.User;
import com.iposhka.repository.SessionRepository;
import com.iposhka.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@ActiveProfiles("test")
@WebAppConfiguration
@SpringJUnitConfig(classes = TestConfig.class)
class SessionCleanupSchedulerIT {

    @Autowired
    private SessionCleanupScheduler sessionCleanupScheduler;

    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    @Sql("/db/insert-data.sql")
    void cleanExpiredSessions(){
        User user = User.builder()
                .id(1)
                .login("iposhka")
                .password("securepassword")
                .build();

        Session expiredSession = Session.builder()
                .id(UUID.randomUUID())
                .user(user)
                .expiresAt(LocalDateTime.now().minusDays(40))//Вычел 40 дней, чтобы наверняка сессия стала невалидной
                .build();

        sessionRepository.save(expiredSession);

        sessionCleanupScheduler.cleanExpiredSessions();

        Optional<Session> maybeSession = sessionRepository.findByUUID(expiredSession.getId());

        assertThat(maybeSession.isPresent())
                .as("Expired session should be deleted after cleanup")
                .isEqualTo(false);
    }

    @Test
    void cleanShouldNotDeleteValidSessions() {
        User user = User.builder()
                .login("user")
                .password("securepassword")
                .build();

        userRepository.save(user);

        Session validSession = Session.builder()
                .id(UUID.randomUUID())
                .user(user)
                .expiresAt(LocalDateTime.now().plusDays(2))
                .build();

        sessionRepository.save(validSession);

        sessionCleanupScheduler.cleanExpiredSessions();

        Optional<Session> maybeSession = sessionRepository.findByUUID(validSession.getId());

        assertThat(maybeSession.isPresent())
                .as("Valid session should not be deleted")
                .isTrue();
    }
}