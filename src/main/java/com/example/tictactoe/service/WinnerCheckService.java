package com.example.tictactoe.service;

public class WinnerCheckService {

    public static boolean checkWinner(String[][] board, String symbol) {
        for (int i = 0; i < 3; i++) {
            if (checkRows(board, symbol, i) || checkColumns(board, symbol, i)) {
                return true;
            }
        }
        return checkDiagonals(board, symbol);
    }

    public static boolean checkDraw(String[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == null) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean checkRows(String[][] board, String symbol, int iterator) {
        return board[iterator][0] != null && board[iterator][0].equals(symbol)
                && board[iterator][1] != null && board[iterator][1].equals(symbol)
                && board[iterator][2] != null && board[iterator][2].equals(symbol);
    }

    private static boolean checkColumns(String[][] board, String symbol, int iterator) {
        return board[0][iterator] != null && board[0][iterator].equals(symbol)
                && board[1][iterator] != null && board[1][iterator].equals(symbol)
                && board[2][iterator] != null && board[2][iterator].equals(symbol);
    }

    private static boolean checkDiagonals(String[][] board, String symbol) {
        if (board[0][0] != null && board[0][0].equals(symbol)
                && board[1][1] != null && board[1][1].equals(symbol)
                && board[2][2] != null && board[2][2].equals(symbol)) {
            return true;
        }
        // reverse diagonal
        return board[0][2] != null && board[0][2].equals(symbol)
                && board[1][1] != null && board[1][1].equals(symbol)
                && board[2][0] != null && board[2][0].equals(symbol);
    }
}
