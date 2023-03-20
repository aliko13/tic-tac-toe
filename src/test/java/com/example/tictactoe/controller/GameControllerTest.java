package com.example.tictactoe.controller;

import com.example.tictactoe.dto.GameRequestDTO;
import com.example.tictactoe.dto.GameResponseDTO;
import com.example.tictactoe.dto.MoveRequestDTO;
import com.example.tictactoe.dto.PlayerDTO;
import com.example.tictactoe.service.GameService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(GameController.class)
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testCreateGame() throws Exception {
        // given
        long gameId = 1L;
        long firstPlayerId = 1L;
        long secondPlayerId = 2L;
        String firstPlayerName = "Player 1";
        String secondPlayerName = "Player 2";

        PlayerDTO player1 = PlayerDTO.builder().id(firstPlayerId).name(firstPlayerName).symbol("x").build();
        PlayerDTO player2 = PlayerDTO.builder().id(secondPlayerId).name(secondPlayerName).symbol("o").build();
        List<PlayerDTO> players = List.of(player1, player2);

        GameRequestDTO requestDTO = GameRequestDTO.builder().firstPlayer(firstPlayerName).secondPlayer(secondPlayerName).build();
        GameResponseDTO responseDTO = GameResponseDTO.builder().id(gameId).players(players).build();

        when(gameService.createGame(any(GameRequestDTO.class))).thenReturn(responseDTO);

        // when / then
        String contentAsString = mockMvc.perform(post("/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        GameResponseDTO response = objectMapper.readValue(contentAsString, GameResponseDTO.class);
        assertThat(response.getId()).isEqualTo(gameId);
        assertThat(response.getPlayers()).hasSize(2);
    }

    @Test
    public void testMakeMove() throws Exception {
        // given
        long gameId = 1L;
        long playerId = 3L;
        GameResponseDTO game = GameResponseDTO.builder().id(gameId).winnerId(playerId).build();
        MoveRequestDTO moveRequestDTO = MoveRequestDTO.builder().playerId(playerId).col(1).row(1).build();

        given(gameService.makeMove(eq(gameId), eq(playerId), anyInt(), anyInt())).willReturn(game);

        // when / then
        String contentAsString = mockMvc.perform(post("/games/1/moves")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(moveRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        GameResponseDTO response = objectMapper.readValue(contentAsString, GameResponseDTO.class);
        assertThat(response)
                .extracting(GameResponseDTO::getId, GameResponseDTO::getWinnerId)
                .containsExactly(gameId, playerId);
    }
}

