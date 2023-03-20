package com.example.tictactoe.controller;

import com.example.tictactoe.dto.GameRequestDTO;
import com.example.tictactoe.dto.GameResponseDTO;
import com.example.tictactoe.dto.MoveRequestDTO;
import com.example.tictactoe.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/games")
public class GameController extends HandlerHelper<GameResponseDTO> {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public ResponseEntity<GameResponseDTO> createGame(@RequestBody GameRequestDTO request) {
        GameResponseDTO response = gameService.createGame(request);
        return toResponseEntity(HttpStatus.CREATED, response);
    }

    @PostMapping("/{gameId}/moves")
    public ResponseEntity<GameResponseDTO> makeMove(@PathVariable Long gameId, @RequestBody MoveRequestDTO request) throws Exception {
        GameResponseDTO response = gameService.makeMove(gameId, request.getPlayerId(), request.getRow(), request.getCol());
        return toResponseEntity(HttpStatus.OK, response);
    }
}
