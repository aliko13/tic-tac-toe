package com.example.tictactoe.exception;

public class InvalidMoveException extends Exception {
    public InvalidMoveException() {
        super("The cell is already occupied!");
    }
}
