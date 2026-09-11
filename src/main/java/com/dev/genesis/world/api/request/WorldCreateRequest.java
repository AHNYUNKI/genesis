package com.dev.genesis.world.api.request;

import com.dev.genesis.world.domain.World;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record WorldCreateRequest(
        @NotBlank(message = "월드 이름은 필수 입력 항목입니다.")
        @Size(max = World.MAX_NAME_LENGTH, message = "월드 이름은 20자 이하여야 합니다.")
        String name
) {
}
