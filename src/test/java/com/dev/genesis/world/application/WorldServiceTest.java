package com.dev.genesis.world.application;

import com.dev.genesis.support.ServiceTestSupport;
import com.dev.genesis.common.exception.BusinessException;
import com.dev.genesis.world.domain.Status;
import com.dev.genesis.world.domain.World;
import com.dev.genesis.world.domain.repository.WorldRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WorldServiceTest extends ServiceTestSupport {

    @Autowired
    WorldRepository worldRepository;

    @Autowired
    WorldService worldService;

    @AfterEach
    void tearDown() {
        worldRepository.deleteAllInBatch();
    }

    @Test
    void 세계를_생성한다() {
        //given
        String name = "test";

        //when
        worldService.create(name);

        //then
        List<World> worlds = worldRepository.findAll();

        assertThat(worlds).hasSize(1);

        World world = worlds.get(0);

        assertThat(world.getId()).isNotNull();
        assertThat(world.getName()).isEqualTo("test");
        assertThat(world.getStatus()).isEqualTo(Status.READY);
        assertThat(world.getCurrentTick()).isZero();
        assertThat(world.getCreatedAt()).isNotNull();
    }

    @Test
    void 세계를_생성할_때_중복된_이름은_생성할_수_없다() {
        //given
        World originWorld = World.create("test");
        worldRepository.save(originWorld);

        String duplicatedName = "test";

        //when //then
        assertThatThrownBy(() -> worldService.create(duplicatedName))
                .isInstanceOf(BusinessException.class)
                .hasMessage("이미 존재하는 월드입니다.");

        assertThat(worldRepository.count()).isEqualTo(1L);
    }

}
