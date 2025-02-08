package com.iposhka.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sessions")
@Getter
@Setter
@ToString(exclude = "user")
public class Session {
    @Id
    @Column(name = "id", unique = true, nullable = false, length = 36)
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "expires_at", nullable = false, unique = true)
    private LocalDateTime expiresAt;
}