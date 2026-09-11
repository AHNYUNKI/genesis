package com.dev.genesis.world.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {
    READY("준비"),
    RUNNING("실행"),
    PAUSED("멈춤"),
    FINISHED("완료");

    private final String description;

}
