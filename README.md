# Multiplayer Tic-Tac-Toe over LAN (Java) 

A simple, terminal-based multiplayer Tic-Tac-Toe game that works over a local network. It uses raw Java socket networking (`java.net`) and standard TCP connections to let two players join and play from different computers on the same Wi-Fi network.

---

## Features

*   **Real-Time LAN Play**: Play with a friend on two different machines using the same local network.
*   **Simple Network Protocol**: Uses clear, lightweight text commands to sync turns between devices.
*   **Clean Code Separation**: The game rules and board states are kept completely separate from the networking logic.
*   **Zero External Dependencies**: Built entirely with the native Java Standard Library.

---

## How It Works

This project uses a standard client-server setup. **Player 1 (Server)** hosts the game session as `X`, and **Player 2 (Client)** connects directly to the server's IP address to play as `O`.

### Grid System
The board maps 9 squares to numbers `0` through `8` in your terminal window:

```text
 0 | 1 | 2 
-----------
 3 | 4 | 5 
-----------
 6 | 7 | 8 
```

### Network Messages
The game keeps both screens in sync by passing simple messages over the connection:
*   `MOVE <0-8>` – Sent by the client to register a move.
*   `OPPONENT_MOVED <0-8>` – Sent by the server to update the client's board.
*   `VICTORY` / `DEFEAT` / `TIE` – Sent at the end of a match to trigger the final screen.

---

## File Structure

```text
tic-tac-toe-lan/
├── README.md
└── src/
    └── com/
        └── tictactoe/
            ├── GameEngine.java      # Rules, turns, and win/tie checks
            ├── TicTacToeServer.java  # Hosts the connection (Player 1)
            └── TicTacToeClient.java  # Connects to the host (Player 2)
```

---

## How to Set Up and Run

Make sure you have **Java JDK 8 or higher** installed.

### 1. Download the Project
```bash
git clone https://github.com
cd tic-tac-toe-lan/src
```

### 2. Compile the Files
```bash
javac com/tictactoe/*.java
```

### 3. Start Player 1 (The Host)
Run the server program first so it can open a port and wait for a connection:
```bash
java com.tictactoe.TicTacToeServer
```

### 4. Start Player 2 (The Guest)
Open another terminal window (either on the same computer or a different PC on your network) and run:
```bash
java com.tictactoe.TicTacToeClient
```
*   **Playing on one PC**: Type `localhost` when the client asks for an IP address.
*   **Playing on two PCs**: Find your server PC's local IP address (like `192.168.1.50`) and type that into the client terminal.

---

## Future Improvements
*   [ ] **In-Game Chat**: Use Java threads to let players type chat messages back and forth during the game.
*   [ ] **GUI Interface**: Add a simple visual grid UI using JavaFX or Swing instead of the terminal.
