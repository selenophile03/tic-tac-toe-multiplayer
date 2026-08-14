package com.tictactoe;

public class GameEngine {
    private char[] board = new char[9];
    private char currentTurn = 'X';

    public GameEngine() {
        for (int i = 0; i < 9; i++) board[i] = ' ';
    }

    public boolean isValidMove(int index) {
        return index >= 0 && index < 9 && board[index] == ' ';
    }

    public void makeMove(int index, char player) {
        board[index] = player;
    }

    public void switchTurn() {
        currentTurn = (currentTurn == 'X') ? 'O' : 'X';
    }

    public char getCurrentTurn() {
        return currentTurn;
    }

    public boolean checkWin(char player) {
        int[][] winConditions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // Rows
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // Columns
            {0, 4, 8}, {2, 4, 6}             // Diagonals
        };
        for (int[] condition : winConditions) {
            if (board[condition[0]] == player && 
                board[condition[1]] == player && 
                board[condition[2]] == player) {
                return true;
            }
        }
        return false;
    }

    public boolean isBoardFull() {
        for (char cell : board) {
            if (cell == ' ') return false;
        }
        return true;
    }

    public void printBoard() {
        System.out.println("\n " + board[0] + " | " + board[1] + " | " + board[2]);
        System.out.println("-----------");
        System.out.println(" " + board[3] + " | " + board[4] + " | " + board[5]);
        System.out.println("-----------");
        System.out.println(" " + board[6] + " | " + board[7] + " | " + board[8] + "\n");
    }
}
