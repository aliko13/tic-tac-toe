package com.example.tictactoe.exception;

public class PlayerNotFoundException extends Exception {
    public PlayerNotFoundException() {
        super();
    }
    public PlayerNotFoundException(String message) {
        super(message);
    }
}
