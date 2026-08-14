package com.tictactoe;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TicTacToeClient {
    private static final int PORT = 58901;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Server IP Address (use 'localhost' for same PC): ");
        String serverIp = scanner.nextLine();

        try (Socket socket = new Socket(serverIp, PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.println("Connected to the game server!");
            GameEngine game = new GameEngine();
            char myMark = 'O';
            char opponentMark = 'X';

            game.printBoard();

            while (true) {
                if (game.getCurrentTurn() == opponentMark) {
                    System.out.println("Waiting for Player 1 (X) to move...");
                    String serverMessage = in.readLine();

                    if (serverMessage == null) break;

                    if (serverMessage.startsWith("OPPONENT_MOVED")) {
                        int move = Integer.parseInt(serverMessage.split(" ")[1]);
                        game.makeMove(move, opponentMark);
                        System.out.println("Player 1 played position: " + move);
                        game.printBoard();
                        game.switchTurn();
                    } else if (serverMessage.equals("DEFEAT")) {
                        System.out.println("❌ You Lose!");
                        break;
                    } else if (serverMessage.equals("TIE")) {
                        System.out.println("🤝 It's a Tie!");
                        break;
                    }
                }

                // If turn switched to client after server's move tracking
                if (game.getCurrentTurn() == myMark) {
                    System.out.print("Your turn (O). Enter position (0-8): ");
                    int move = scanner.nextInt();

                    while (!game.isValidMove(move)) {
                        System.out.print("Invalid move. Enter position (0-8): ");
                        move = scanner.nextInt();
                    }

                    game.makeMove(move, myMark);
                    out.println("MOVE " + move); // Notify server
                    game.printBoard();

                    // Instantly check incoming state from server regarding your win/loss status
                    String serverFeedback = in.readLine();
                    if ("VICTORY".equals(serverFeedback)) {
                        System.out.println("🎉 You Win!");
                        break;
                    } else if ("TIE".equals(serverFeedback)) {
                        System.out.println("🤝 It's a Tie!");
                        break;
                    }
                    game.switchTurn();
                }
            }
        } catch (IOException e) {
            System.out.println("Client exception: " + e.getMessage());
        }
    }
}
