package com.dev.genesis.world.api;

import com.dev.genesis.support.ControllerTestSupport;
import com.dev.genesis.common.exception.BusinessException;
import com.dev.genesis.common.exception.ErrorCode;
import com.dev.genesis.world.api.request.WorldCreateRequest;
import com.dev.genesis.world.application.WorldService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WorldController.class)
class WorldControllerTest extends ControllerTestSupport {

    @MockitoBean
    private WorldService worldService;

    @Test
    void 월드를_생성하는_API를_호출한다() throws Exception {
        // given
        WorldCreateRequest request = new WorldCreateRequest("test");

        // when // then
        mockMvc.perform(post("/v1/worlds")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"));

        verify(worldService).create("test");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t"})
    void 이름이_없거나_공백이면_400을_반환한다(String name) throws Exception {
        // given
        WorldCreateRequest request = new WorldCreateRequest(name);

        // when // then
        mockMvc.perform(post("/v1/worlds")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("name: 월드 이름은 필수 입력 항목입니다."));

        verifyNoInteractions(worldService);
    }

    @Test
    void 이름이_20자를_초과하면_400을_반환한다() throws Exception {
        // given
        WorldCreateRequest request = new WorldCreateRequest("가".repeat(21));

        // when // then
        mockMvc.perform(post("/v1/worlds")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("name: 월드 이름은 20자 이하여야 합니다."));

        verifyNoInteractions(worldService);
    }

    @Test
    void 이름이_중복되면_409를_반환한다() throws Exception {
        // given
        WorldCreateRequest request = new WorldCreateRequest("test");
        doThrow(new BusinessException(ErrorCode.ALREADY_EXISTED_WORLD))
                .when(worldService).create("test");

        // when // then
        mockMvc.perform(post("/v1/worlds")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value(409))
                .andExpect(jsonPath("$.message").value("이미 존재하는 월드입니다."));
    }

}
