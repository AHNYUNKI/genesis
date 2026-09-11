package com.dev.genesis.world.application;

import com.dev.genesis.common.exception.BusinessException;
import com.dev.genesis.common.exception.ErrorCode;
import com.dev.genesis.world.domain.World;
import com.dev.genesis.world.domain.repository.WorldRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorldService {

    private final WorldRepository worldRepository;

    @Transactional
    public void create(String name) {
        World newWorld = World.create(name);

        if (worldRepository.existsByName(name)) {
            throw new BusinessException(ErrorCode.ALREADY_EXISTED_WORLD);
        }

        worldRepository.save(newWorld);
    }

}
