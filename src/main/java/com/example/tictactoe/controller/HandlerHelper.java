package com.example.tictactoe.controller;

import com.example.tictactoe.exception.GameNotFoundException;
import com.example.tictactoe.exception.GameOverException;
import com.example.tictactoe.exception.InvalidMoveException;
import com.example.tictactoe.exception.PlayerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Component
public class HandlerHelper<T> {

    @ExceptionHandler({PlayerNotFoundException.class, GameOverException.class, InvalidMoveException.class, GameNotFoundException.class})
    public ResponseEntity<String> handleGameExceptions(Exception ex) {
        HttpStatus status;
        if (ex instanceof PlayerNotFoundException || ex instanceof GameNotFoundException) {
            status = HttpStatus.NOT_FOUND;
        } else if (ex instanceof GameOverException || ex instanceof InvalidMoveException) {
            status = HttpStatus.BAD_REQUEST;
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(status).body(ex.getMessage());
    }


    ResponseEntity<T> toResponseEntity(HttpStatus status, T body) {
        return ResponseEntity.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(body);
    }
}
