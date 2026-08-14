package com.tictactoe;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TicTacToeServer {
    private static final int PORT = 58901;

    public static void main(String[] args) {
        System.out.println("Tic-Tac-Toe Server is starting...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Waiting for Player 2 (Client) to connect...");
            
            try (Socket socket = serverSocket.accept();
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 Scanner scanner = new Scanner(System.in)) {

                System.out.println("Player 2 connected! Game starting.");
                GameEngine game = new GameEngine();
                char myMark = 'X';
                char opponentMark = 'O';

                game.printBoard();

                while (true) {
                    if (game.getCurrentTurn() == myMark) {
                        // Server's turn to move
                        System.out.print("Your turn (X). Enter position (0-8): ");
                        int move = scanner.nextInt();

                        while (!game.isValidMove(move)) {
                            System.out.print("Invalid move. Enter position (0-8): ");
                            move = scanner.nextInt();
                        }

                        game.makeMove(move, myMark);
                        out.println("OPPONENT_MOVED " + move); // Notify client
                        game.printBoard();

                        if (game.checkWin(myMark)) {
                            System.out.println("🎉 You Win!");
                            out.println("DEFEAT");
                            break;
                        } else if (game.isBoardFull()) {
                            System.out.println("🤝 It's a Tie!");
                            out.println("TIE");
                            break;
                        }
                        game.switchTurn();
                    } else {
                        // Waiting for Client's turn
                        System.out.println("Waiting for Player 2 (O) to move...");
                        String response = in.readLine();
                        
                        if (response != null && response.startsWith("MOVE")) {
                            int move = Integer.parseInt(response.split(" ")[1]);
                            game.makeMove(move, opponentMark);
                            System.out.println("Player 2 played position: " + move);
                            game.printBoard();

                            if (game.checkWin(opponentMark)) {
                                System.out.println("❌ You Lose!");
                                out.println("VICTORY");
                                break;
                            } else if (game.isBoardFull()) {
                                System.out.println("🤝 It's a Tie!");
                                out.println("TIE");
                                break;
                            }
                            game.switchTurn();
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Server exception: " + e.getMessage());
        }
    }
}
