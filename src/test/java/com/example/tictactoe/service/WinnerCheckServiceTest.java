package com.example.tictactoe.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WinnerCheckServiceTest {
    @Test
    public void testCheckWinner() {
        String[][] board = {{"X", "O", null}, {"X", "O", null}, {"X", null, "O"}};
        assertTrue(WinnerCheckService.checkWinner(board, "X"));

        board = new String[][]{{"O", "O", null}, {"X", "X", "X"}, {null, null, "O"}};
        assertTrue(WinnerCheckService.checkWinner(board, "X"));

        board = new String[][]{{"O", "X", null}, {"X", "O", "O"}, {"O", null, "O"}};
        assertTrue(WinnerCheckService.checkWinner(board, "O"));

        board = new String[][]{{"O", "X", null}, {"X", "O", null}, {"O", null, "X"}};
        assertFalse(WinnerCheckService.checkWinner(board, "X"));
    }

    @Test
    public void testCheckDraw() {
        String[][] board = {{"X", "O", "X"}, {"O", "X", "O"}, {"O", "X", "O"}};
        assertTrue(WinnerCheckService.checkDraw(board));

        board = new String[][]{{"O", "O", "X"}, {"X", "X", "O"}, {"O", "X", null}};
        assertFalse(WinnerCheckService.checkDraw(board));
    }
}
