package com.dev.genesis.world.api;

import com.dev.genesis.common.api.ApiResponse;
import com.dev.genesis.world.api.request.WorldCreateRequest;
import com.dev.genesis.world.application.WorldService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WorldController {

    private final WorldService worldService;

    @PostMapping("/v1/worlds")
    public ApiResponse<Void> createWorld(@Valid @RequestBody WorldCreateRequest request) {
        worldService.create(request.name());

        return ApiResponse.ok();
    }

}
