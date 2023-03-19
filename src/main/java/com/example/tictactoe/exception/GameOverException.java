package com.example.tictactoe.exception;

public class GameOverException extends Exception {
    public GameOverException() {
        super("Game is over!");
    }
}
