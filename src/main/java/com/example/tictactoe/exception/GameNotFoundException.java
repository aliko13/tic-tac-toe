package com.example.tictactoe.exception;

public class GameNotFoundException extends Exception {
    public GameNotFoundException() {
        super("Requested game is not found!");
    }
}
