package com.dev.genesis.world.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.dev.genesis.world.domain.Status.READY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class World {

    public static final int MAX_NAME_LENGTH = 20;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "world_name", nullable = false, unique = true, length = MAX_NAME_LENGTH)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "world_status", nullable = false)
    private Status status;

    @Column(name = "world_currenttick", nullable = false)
    private long currentTick;

    @Column(name = "world_createat")
    private LocalDateTime createdAt;

    private World(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("월드 이름은 필수 입력 항목입니다.");
        }
        if (name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("월드 이름은 20자 이하여야 합니다.");
        }

        this.name = name;
        this.status = READY;
        this.currentTick = 0L;
    }

    public static World create(String name) {
        return new World(name);
    }

    @PrePersist
    private void prePersist() {
        createdAt = LocalDateTime.now();
    }

}
