package Game;

import static Game.BattleshipData.HORIZONTAL;
import static Game.BattleshipData.OCCUPIED;
import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;

public class Server extends JFrame implements BattleshipData {
//using enum interface for now    
    // public final int EMPTY = 0;
    // public final int OCCUPIED = 1;
    //public final int DESTROYED = 2;

    // Text area for displaying contents
    private JTextArea jta = new JTextArea();

    private int[][] player1Board, player2Board;

    public static void main(String[] args) {
        new Server();
    }

    public Server() {
        // Place text area on the frame
        setLayout(new BorderLayout());
        add(new JScrollPane(jta), BorderLayout.CENTER);
        setTitle("Battleship Server");
        setSize(500, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true); // It is necessary to show the frame here!

        try {
            // Create a server socket
            ServerSocket serverSocket = new ServerSocket(8000);
            jta.append("MultiThreadServer started at " + new Date() + '\n');

            // Number the client
            int clientNo = 1;

            while (true) {
                jta.append(new Date()
                        + ": Wait for players to join session " + clientNo + '\n');

                // Connect to player 1
                Socket player1 = serverSocket.accept();

                jta.append(new Date() + ": Player 1 joined session "
                        + clientNo + '\n');
                jta.append("Player 1's IP address"
                        + player1.getInetAddress().getHostAddress() + '\n');

                // Notify that the player is Player 1
                new DataOutputStream(
                        player1.getOutputStream()).writeInt(1);
                // Connect to player 2
                Socket player2 = serverSocket.accept();

                jta.append(new Date()
                        + ": Player 2 joined session " + clientNo + '\n');
                jta.append("Player 2's IP address"
                        + player2.getInetAddress().getHostAddress() + '\n');

                // Notify that the player is Player 2
                new DataOutputStream(
                        player2.getOutputStream()).writeInt(2);

                // Display this session and increment session number
                jta.append(new Date() + ": Start a thread for session "
                        + clientNo++ + '\n');

                // Create a new thread for this session of two players
                HandleClients task = new HandleClients(player1, player2);

                // Start the new thread
                new Thread(task).start();
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    // Inner class
    // Define the thread class for handling new connection
    class HandleClients implements Runnable {

        private Socket player1; // A connected socket
        private Socket player2;

        DataInputStream player1Input;
        DataOutputStream player1Output;

        DataInputStream player2Input;
        DataOutputStream player2Output;

        //creates grids that store the status of each tile on both game boards
        private int[][] player1Board, player2Board;
        private Ship[] player1Ships, player2Ships;
        private int turnNumber;

        /**
         * Construct a thread
         */
        public HandleClients(Socket p1, Socket p2) {
            this.player1 = p1;
            this.player2 = p2;
            player1Board = new int[SIDE_LENGTH][SIDE_LENGTH];
            player2Board = new int[SIDE_LENGTH][SIDE_LENGTH];
            player1Ships = new Ship[SHIP_COUNT];
            player2Ships = new Ship[SHIP_COUNT];
            turnNumber = 1;

            //initialize boards with empty tiles
            /*redundant as arrays have value 0/EMPTY by default
            for (int ii = 0; ii < SIDE_LENGTH; ii++) {
                for (int jj = 0; jj < SIDE_LENGTH; jj++) {
                    player1Board[ii][jj] = EMPTY;
                    player1Board[ii][jj] = EMPTY;
                }
            }
             */
        }

        /**
         * Run a thread
         */
        @Override
        public void run() {
            try {
                // Create data input and output streams
                player1Input = new DataInputStream(player1.getInputStream());
                player1Output = new DataOutputStream(player1.getOutputStream());

                player2Input = new DataInputStream(player2.getInputStream());
                player2Output = new DataOutputStream(player2.getOutputStream());

                player1Output.write(0);
                player2Output.write(0);//pings both clients once they are connected
                
                placeShips();
                runFiringPhase();

            } catch (IOException e) {
                System.err.println("client-server connection failed");
            }
        }

        //Begins the phase of the turn where the player will shoot at a location of the enemy board
        public void runFiringPhase() throws IOException {
            //lets players take turns firing at each other; broken when a player wins
            while (true) {
                player2Output.writeChar(1);//sends a ping to the client
                int x = player1Input.readInt();//receives the coordinates of player 1's attack
                int y = player1Input.readInt();

                switch (player2Board[x][y]) //assumes the player is not targeting an already attacked tile
                {
                    case OCCUPIED:
                        player2Board[x][y] = DESTROYED;
                        player1Output.writeInt(1);//sends confirmation of hit
                        break;
                    case EMPTY:
                        player2Board[x][y] = MISS;
                        player1Output.writeInt(0);//sends confirmation of miss
                        break;
                    //case if a player attacks a tile twice, currently sending a number without a response
                    default:
                        player1Output.writeInt(3);
                        break;
                }

                player1Output.writeChar(1);//sends a ping to the client
                x = player2Input.readInt();//receives the coordinates of player 2's attack
                y = player2Input.readInt();

                switch (player1Board[x][y])//assumes the player is not targeting an already attacked tile
                {
                    case OCCUPIED:
                        player1Board[x][y] = DESTROYED;
                        player2Output.writeInt(1);//sends confirmation of hit
                        break;
                    case EMPTY:
                        player1Board[x][y] = MISS;
                        player2Output.writeInt(0);//sends confirmation of miss
                        break;
                    //case if a player attacks a tile twice, currently sending a number without a response
                    default:
                        player2Output.writeInt(3);
                        break;
                }
            }

        }

        //Places the ships of the players on the server side for operations
        public void placeShips() throws IOException {
            //runs 5 turns of placing ships 
            for (int ii = 0; ii < SHIP_COUNT; ii++) {
                player1Output.write(0);//sends ping to let p1 start

                //adds a ship along a line using a coordinate, orientation, and ship size
                placeShip(player1Board, player1Input.readInt(), player1Input.readInt(), player1Input.readInt(),
                        player1Input.readInt());

               // player2Output.write(0);
                placeShip(player2Board, player2Input.readInt(), player1Input.readInt(), player1Input.readInt(),
                        player1Input.readInt());
            }
        }

        public void placeShip(int[][] board, int x, int y, int orientation, int shipSize) {
            //Point[] pointArray = new Point[shipSize];//stores the points to create a ship, may not even use ship
            //objects
           // Point point = new Point(x, y);

            String player;
            if (board == player1Board) {
                player = "Player 1 ";
            } else {
                player = "Player 2 ";
            }

            jta.append(player + "placed a ship at " + x + "," + y + " size: " + shipSize + " orientation: " + orientation);
            //if orientation is horizontal, starting point is on left 
            if (orientation == HORIZONTAL) {
                for (int jj = 0; jj < shipSize; jj++) {
                    board[x + jj][y] = OCCUPIED; //makes each location along the line contain a ship tile
                   // pointArray[jj] = new Point(point.x + jj, point.y);
                }
            } else //verttical orientation, starts at top and extends downward
            {
                for (int jj = 0; jj < shipSize; jj++) {
                    board[x][y - jj] = OCCUPIED;
                   // pointArray[jj] = new Point(point.x, point.y - jj);
                }
                //player1Ships[ii] = new Ship(pointArray){};
            }

        }

        /**
         * Checks to see if either player has won. Returns 1 if player 1 wins, 2
         * if player 2 wins, and 0 if no player wins on a turn
         *
         * @return The victory value
         */
        public int checkVictory() {
            //check for p1 victory 
            if (turnNumber > 13)//only checks once enough moves have been made to win
            {
                boolean p1Victory = true;
                for (Ship ship : player2Ships)//iterates through
                {
                    p1Victory = p1Victory && ship.isSunk();

                    if (p1Victory == false) {
                        break;

                        //TODO Write Victory code
                    }
                }
            }
            //check for p2 victory 
            if (turnNumber > 13)//only checks once enough moves have been made to win
            {
                boolean p2Victory = true;
                for (Ship ship : player1Ships)//iterates through
                {
                    p2Victory = p2Victory && ship.isSunk();

                    if (p2Victory == false) {
                        break;

                        //TODO Write Victory code
                    }
                }
            }
            turnNumber++;
            return 0;
        }

    }
}
