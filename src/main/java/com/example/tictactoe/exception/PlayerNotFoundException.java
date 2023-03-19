package com.example.tictactoe.exception;

public class PlayerNotFoundException extends Exception {
    public PlayerNotFoundException() {
        super("Player does not exist!");
    }
}
