package com.example.tictactoe.service;

import com.example.tictactoe.convert.GameConvertService;
import com.example.tictactoe.dto.GameResponseDTO;
import com.example.tictactoe.entity.Game;
import com.example.tictactoe.entity.Player;
import com.example.tictactoe.exception.GameNotFoundException;
import com.example.tictactoe.exception.GameOverException;
import com.example.tictactoe.exception.InvalidMoveException;
import com.example.tictactoe.exception.PlayerNotFoundException;
import com.example.tictactoe.repository.GameRepository;
import com.example.tictactoe.repository.PlayerRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.tictactoe.service.WinnerCheckService.checkDraw;
import static com.example.tictactoe.service.WinnerCheckService.checkWinner;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final GameConvertService gameConvertService;
    private final PlayerRepository playerRepository;

    public GameService(GameRepository gameRepository,
                       GameConvertService gameConvertService,
                       PlayerRepository playerRepository) {
        this.gameRepository = gameRepository;
        this.gameConvertService = gameConvertService;
        this.playerRepository = playerRepository;
    }

    @Cacheable(value = "games", key = "#gameId")
    public Game getGame(Long gameId) throws GameNotFoundException {
        return gameRepository.findById(gameId).orElseThrow(GameNotFoundException::new);
    }

    @CachePut(value = "games", key = "#game.id")
    public Game saveGame(Game game) {
        return gameRepository.saveAndFlush(game);
    }

    public GameResponseDTO createGame(String firstPlayerName, String secondPlayerName) {
        Player firstPlayer = playerRepository.saveAndFlush(new Player(firstPlayerName, "X"));
        Player secondPlayer =  playerRepository.saveAndFlush(new Player(secondPlayerName, "O"));
        Game game = Game.builder()
                .players(List.of(firstPlayer, secondPlayer))
                .playerOnTurn(firstPlayer.getId())
                .inProgress(true)
                .build();
        Game savedGame = saveGame(game);
        return gameConvertService.toGameResponseDto(savedGame);
    }

    @CacheEvict(value = "games", key = "#gameId")
    public synchronized Game makeMove(Long gameId, long playerId, int row, int col) throws Exception {
        Game game = getGame(gameId);
        if (game.getPlayerOnTurn() != playerId) {
            throw new PlayerNotFoundException();
        }
        if (!game.isInProgress()) {
            throw new GameOverException();
        }
        String[][] board = game.getBoard();
        if (board[row][col] != null) {
            throw new InvalidMoveException();
        }
        board[row][col] = game.getCurrentPlayer().getSymbol();
        if (checkWinner(board, game.getCurrentPlayer().getSymbol())) {
            game.setWinnerId(game.getCurrentPlayer().getId());
            game.setInProgress(false);
        } else if (checkDraw(board)) {
            game.setInProgress(false);
        } else {
            game.switchPlayer();
        }
        saveGame(game);
        return game;
    }
}


