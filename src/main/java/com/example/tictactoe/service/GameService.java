package com.example.tictactoe.service;

import com.example.tictactoe.convert.GameConvertService;
import com.example.tictactoe.dto.GameRequestDTO;
import com.example.tictactoe.dto.GameResponseDTO;
import com.example.tictactoe.entity.Game;
import com.example.tictactoe.entity.Player;
import com.example.tictactoe.exception.GameNotFoundException;
import com.example.tictactoe.exception.GameOverException;
import com.example.tictactoe.exception.InvalidMoveException;
import com.example.tictactoe.exception.PlayerNotFoundException;
import com.example.tictactoe.repository.GameRepository;
import com.example.tictactoe.repository.PlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Game getGame(Long gameId) throws GameNotFoundException {
        return gameRepository.findById(gameId).orElseThrow(GameNotFoundException::new);
    }

    public Game saveGame(Game game) {
        return gameRepository.saveAndFlush(game);
    }

    @Transactional
    public GameResponseDTO createGame(GameRequestDTO requestDTO) {
        Player firstPlayer = new Player(requestDTO.getFirstPlayer(), "X");
        Player secondPlayer = new Player(requestDTO.getSecondPlayer(), "O");

        firstPlayer = playerRepository.saveAndFlush(firstPlayer);
        secondPlayer = playerRepository.saveAndFlush(secondPlayer);

        Game game = Game.builder()
                .players(List.of(firstPlayer, secondPlayer))
                .playerOnTurn(firstPlayer.getId())
                .board(new String[3][3])
                .build();

        game = gameRepository.saveAndFlush(game);

        firstPlayer.setGame(game);
        secondPlayer.setGame(game);

        return gameConvertService.toGameResponseDto(game);
    }

    @Transactional
    public synchronized GameResponseDTO makeMove(Long gameId, long playerId, int row, int col) throws Exception {
        Game game = getGame(gameId);
        if (!game.isInProgress()) {
            throw new GameOverException("Game is already ended");
        }
        if (game.getPlayerOnTurn() != playerId) {
            throw new PlayerNotFoundException("Wrong player turn");
        }
        String[][] board = game.getBoard();
        if (board[row][col] != null) {
            throw new InvalidMoveException();
        }
        board[row][col] = game.getCurrentPlayer().getSymbol();
        if (checkWinner(board, game.getCurrentPlayer().getSymbol())) {
            game.setWinnerId(game.getCurrentPlayer().getId());
            game.setInProgress(false);
            throw new GameOverException("Game is over, winner is: "+game.getCurrentPlayer().getName());
        } else if (checkDraw(board)) {
            game.setInProgress(false);
            throw new GameOverException("Game is ended with draw, no winner");
        } else {
            game.switchPlayer();
        }
        game.setBoard(board);
        Game savedGame = saveGame(game);
        return gameConvertService.toGameResponseDto(savedGame);
    }
}


