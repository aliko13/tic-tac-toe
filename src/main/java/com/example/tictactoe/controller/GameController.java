package com.example.tictactoe.controller;

import com.example.tictactoe.dto.GameRequestDTO;
import com.example.tictactoe.dto.GameResponseDTO;
import com.example.tictactoe.dto.MoveRequestDTO;
import com.example.tictactoe.entity.Game;
import com.example.tictactoe.service.GameService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/games")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public GameResponseDTO createGame(@RequestBody GameRequestDTO request) {
        return gameService.createGame(request.getFirstPlayer(), request.getSecondPlayer());
    }

    @PostMapping("/{gameId}/moves")
    public GameResponseDTO makeMove(@PathVariable Long gameId, @RequestBody MoveRequestDTO request) throws Exception {
        return gameService.makeMove(gameId, request.getPlayerId(), request.getRow(), request.getCol());
    }
}
