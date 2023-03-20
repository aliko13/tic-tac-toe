package com.example.tictactoe.service;

import com.example.tictactoe.convert.GameConvertService;
import com.example.tictactoe.dto.GameRequestDTO;
import com.example.tictactoe.dto.GameResponseDTO;
import com.example.tictactoe.entity.AbstractEntity;
import com.example.tictactoe.entity.Game;
import com.example.tictactoe.entity.Player;
import com.example.tictactoe.exception.GameNotFoundException;
import com.example.tictactoe.exception.GameOverException;
import com.example.tictactoe.exception.PlayerNotFoundException;
import com.example.tictactoe.repository.GameRepository;
import com.example.tictactoe.repository.PlayerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private GameConvertService gameConvertService;

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private GameService gameService;

    @Test
    public void testGetGame() throws GameNotFoundException {
        // given
        long gameId = 1L;
        Game game = new Game();
        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));

        // when
        Game result = gameService.getGame(gameId);

        // then
        assertEquals(game, result);
    }

    @Test
    public void testGameNotFoundException() {
        // given
        long gameId = 1L;
        when(gameRepository.findById(gameId)).thenReturn(Optional.empty());

        // when / then
        assertThrows(GameNotFoundException.class, () -> gameService.getGame(gameId));
    }

    @Test
    public void testSaveGame() {
        // given
        Game game = new Game();
        when(gameRepository.saveAndFlush(game)).thenReturn(game);

        // when
        Game result = gameService.saveGame(game);

        // then
        assertEquals(game, result);
    }

    @Test
    public void testCreateGame() {
        // given
        String firstPlayerName = "Player 1";
        String secondPlayerName = "Player 2";

        Player firstPlayer = new Player(firstPlayerName, "X");
        Player secondPlayer = new Player(secondPlayerName, "O");

        GameRequestDTO requestDTO = GameRequestDTO.builder()
                .firstPlayer(firstPlayerName)
                .secondPlayer(secondPlayerName)
                .build();
        GameResponseDTO responseDTO = new GameResponseDTO();

        when(playerRepository.saveAndFlush(firstPlayer)).thenReturn(firstPlayer);
        when(playerRepository.saveAndFlush(secondPlayer)).thenReturn(secondPlayer);

        Game savedGame = new Game();
        savedGame.setPlayers(List.of(firstPlayer, secondPlayer));
        savedGame.setPlayerOnTurn(firstPlayer.getId());
        savedGame.setBoard(new String[3][3]);
        when(gameRepository.saveAndFlush(any(Game.class))).thenReturn(savedGame);

        when(gameConvertService.toGameResponseDto(savedGame)).thenReturn(responseDTO);

        // when
        GameResponseDTO result = gameService.createGame(requestDTO);

        // then
        assertEquals(responseDTO, result);
    }

    @Test
    public void testMakeMove() throws Exception {
        // given
        long gameId = 1L;
        int row = 0;
        int col = 0;
        long firstPlayerId = 1L;
        long secondPlayerId = 2L;

        Field field = AbstractEntity.class.getDeclaredField("id");
        field.setAccessible(true);

        Player firstPlayer = new Player("Alice", "X");
        Player secondPlayer = new Player("Bob", "O");

        field.set(firstPlayer, firstPlayerId);
        field.set(secondPlayer, secondPlayerId);

        Game game = new Game();
        field.set(game, gameId);
        game.setPlayers(List.of(firstPlayer, secondPlayer));
        game.setPlayerOnTurn(firstPlayerId);

        GameResponseDTO gameResponseDTO = new GameResponseDTO();

        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));
        when(gameRepository.saveAndFlush(game)).thenReturn(game);
        when(gameConvertService.toGameResponseDto(game)).thenReturn(gameResponseDTO);

        // when
        gameService.makeMove(gameId, firstPlayerId, row, col);

        // then
        verify(gameRepository).findById(gameId);
        verify(gameConvertService).toGameResponseDto(game);

        assertEquals(secondPlayerId, game.getPlayerOnTurn());
        assertTrue(game.isInProgress());
        assertNull(game.getWinnerId());
        assertThat(game.getBoard()[0][0]).isEqualTo("X");
    }

    @Test
    public void testMakeMoveGameNotFound() {
        // given
        long gameId = 1L;
        long playerId = 1L;
        when(gameRepository.findById(gameId)).thenReturn(Optional.empty());

        // when / then
        assertThrows(GameNotFoundException.class, () -> gameService.makeMove(gameId, playerId, 0, 0));
    }

    @Test
    public void testMakeMoveWrongPlayer() {
        // given
        long gameId = 1L;
        long firstPlayerId = 1L;
        int row = 0;
        int col = 0;

        Player firstPlayer = new Player("Alice", "x");

        Game game = new Game();
        game.setPlayerOnTurn(firstPlayerId);
        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));

        // when / then
        assertThrows(PlayerNotFoundException.class, () -> gameService.makeMove(gameId, firstPlayer.getId(), row, col));
    }

    @Test
    public void testMakeMoveGameOver() {
        // given
        String[][] board = {{"X", "O", "X"}, {"O", "X", "O"}, {"O", "X", "O"}};
        Game game = Game.builder()
                .players(List.of(new Player("Player 1", "X"), new Player("Player 2", "O")))
                .playerOnTurn(1L)
                .board(board)
                .inProgress(false)
                .winnerId(1L)
                .build();
        when(gameRepository.findById(1L)).thenReturn(Optional.ofNullable(game));

        // when / then
        assertThrows(GameOverException.class, () -> gameService.makeMove(1L, 1L, 0, 1));
    }
}
