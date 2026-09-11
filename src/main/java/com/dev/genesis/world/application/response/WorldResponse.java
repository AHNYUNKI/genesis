package com.dev.genesis.world.application.response;

import com.dev.genesis.world.domain.Status;

import java.time.LocalDateTime;

public record WorldResponse(
        String name,
        Status status,
        long currentTick,
        LocalDateTime createdAt
) {
}
