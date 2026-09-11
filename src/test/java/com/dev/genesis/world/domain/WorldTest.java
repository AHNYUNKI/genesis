package com.dev.genesis.world.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WorldTest {

    @Test
    void 월드는_준비_상태와_0_tick으로_생성된다() {
        // when
        World world = World.create("test");

        // then
        assertThat(world.getName()).isEqualTo("test");
        assertThat(world.getStatus()).isEqualTo(Status.READY);
        assertThat(world.getCurrentTick()).isZero();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n"})
    void 이름이_없거나_공백이면_월드를_생성할_수_없다(String name) {
        // when // then
        assertThatThrownBy(() -> World.create(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("월드 이름은 필수 입력 항목입니다.");
    }

    @Test
    void 이름은_20자까지_사용할_수_있다() {
        // given
        String name = "가".repeat(20);

        // when
        World world = World.create(name);

        // then
        assertThat(world.getName()).isEqualTo(name);
    }

    @Test
    void 이름이_20자를_초과하면_월드를_생성할_수_없다() {
        // given
        String name = "가".repeat(21);

        // when // then
        assertThatThrownBy(() -> World.create(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("월드 이름은 20자 이하여야 합니다.");
    }
}
