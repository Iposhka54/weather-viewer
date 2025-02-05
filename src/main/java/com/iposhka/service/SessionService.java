package com.iposhka.service;

import com.iposhka.dto.SessionDto;
import com.iposhka.dto.UserLoginDto;
import com.iposhka.mapper.SessionMapper;
import com.iposhka.mapper.UserMapper;
import com.iposhka.model.Session;
import com.iposhka.model.User;
import com.iposhka.repository.SessionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Optional;
import java.util.UUID;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;
    private final UserMapper userMapper;
    private final SessionMapper sessionMapper;
    private final int sessionTimeout = 300;

    public SessionService(SessionRepository sessionRepository, UserMapper userMapper, SessionMapper sessionMapper) {
        this.sessionRepository = sessionRepository;
        this.userMapper = userMapper;
        this.sessionMapper = sessionMapper;
    }

    @Transactional
    public SessionDto findOrCreateSession(UserLoginDto userLoginDto) {

        Optional<Session> maybeSession = sessionRepository.findByUserId(userLoginDto.getId());
        Session sessionEntity = maybeSession.orElseGet(() -> {
            User entity = userMapper.toEntity(userLoginDto);

            Session session = Session.builder()
                    .id(UUID.randomUUID())
                    .user(entity)
                    .expiresAt(LocalDateTime.now().plusSeconds(sessionTimeout))
                    .build();

            sessionRepository.save(session);
            return session;
        });
        return sessionMapper.toDto(sessionEntity);
    }
}
