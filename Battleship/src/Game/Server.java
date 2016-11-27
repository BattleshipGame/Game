package Game;

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
    
                    DataInputStream player1Input ;
                DataOutputStream player1Output;

                DataInputStream player2Input;
                DataOutputStream player2Output;

    public static void main(String[] args) {
        new Server();
    }

    public Server() {
        // Place text area on the frame
        setLayout(new BorderLayout());
        add(new JScrollPane(jta), BorderLayout.CENTER);
        setTitle("Battleship Server");
        setSize(500, 300);
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
                        player1.getOutputStream()).writeInt(1);//was originally a constant defined as 1
                // Connect to player 2
                Socket player2 = serverSocket.accept();

                jta.append(new Date()
                        + ": Player 2 joined session " + clientNo + '\n');
                jta.append("Player 2's IP address"
                        + player2.getInetAddress().getHostAddress() + '\n');

                // Notify that the player is Player 2
                new DataOutputStream(
                        player2.getOutputStream()).writeInt(2);//see comment above

                // Display this session and increment session number
                jta.append(new Date() + ": Start a thread for session "
                        + clientNo++ + '\n');

                // Create a new thread for this session of two players
                HandleAClient task = new HandleAClient(player1, player2);

                // Start the new thread
                new Thread(task).start();
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    // Inner class
    // Define the thread class for handling new connection
    class HandleAClient implements Runnable {

        private Socket player1; // A connected socket
        private Socket player2;

        //creates grids that store the status of each tile on both game boards
        private int[][] player1Board, player2Board;
        private Ship[] player1Ships, player2Ships;
        
        /**
         * Construct a thread
         */
        public HandleAClient(Socket p1, Socket p2) {
            this.player1 = p1;
            this.player2 = p2;
            player1Board = new int[SIDE_LENGTH][SIDE_LENGTH];
            player2Board = new int[SIDE_LENGTH][SIDE_LENGTH];
            player1Ships = new Ship[SHIP_COUNT];
            player2Ships = new Ship[SHIP_COUNT];

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

                //runs 5 turns of placing ships 
                for (int ii = 0; ii < SHIP_COUNT; ii++) {
                    //at current stage, adds a ship along a line using a coordinate, orientation, and 
                    //ship size
                    Point point = new Point(player1Input.readInt(), player1Input.readInt());
                    int orientation = player1Input.readInt();
                    int shipSize = player1Input.readInt();
                    Point[] pointArray = new Point[shipSize];//stores the points to create a ship

                    //if orientation is horizontal, starting point is on left 
                    if (orientation == HORIZONTAL) {
                        for (int jj = 0; jj < shipSize; jj++) {
                            player1Board[point.x + jj][point.y] = OCCUPIED; //makes each location
                            // along the line contain a ship tile
                            pointArray[jj] = new Point(point.x + jj, point.y);
                        }
                    } 
                    else //verttical orientation, starts at top and extends downward
                    {
                        for (int jj = 0; jj < shipSize; jj++) {
                            player1Board[point.x][point.y - jj] = OCCUPIED;
                            pointArray[jj] = new Point(point.x, point.y - jj);
                        }
                        player1Ships[ii] = new Ship(pointArray);
                    }

                    point = new Point(player2Input.readInt(), player2Input.readInt());
                    orientation = player2Input.readInt();
                    shipSize = player2Input.readInt();
                    pointArray = new Point[shipSize];//stores the points to create a ship

                    //orientation of 1 is horizontal, starting point is on left 
                    if (orientation == HORIZONTAL) {
                        for (int jj = 0; jj < shipSize; jj++) {
                            player2Board[point.x + jj][point.y] = OCCUPIED; //makes each location
                            // along the line contain a ship tile
                            pointArray[jj] = new Point(point.x + jj, point.y);
                        }
                    } else //verttical orientation, starts at top and extends downward
                    {
                        for (int jj = 0; jj < shipSize; jj++) {
                            player2Board[point.x][point.y - jj] = OCCUPIED;
                            pointArray[jj] = new Point(point.x, point.y - jj);
                        }
                        player2Ships[ii] = new Ship(pointArray);
                    }
                }

                int turnNumber = 1;
                //lets players take turns firing at each other; broken when a player wins
                while (true) {

                    player1Output.writeChar(1);//sends a ping to the client
                    int x = player1Input.readInt();//receives the coordinates of player 1's attack
                    int y = player1Input.readInt();

                    if (player2Board[x][y] == OCCUPIED)//assumes the player is not targeting an already
                    //attacked tile
                    {
                        player2Board[x][y] = DESTROYED;
                        player1Output.writeInt(1);//sends confirmation of hit
                    } else {
                        player2Board[x][y] = MISS;
                        player1Output.writeInt(0);//sends confirmation of miss
                    }

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

                    player1Output.writeChar(1);//sends a ping to the client
                    x = player2Input.readInt();//receives the coordinates of player 1's attack
                    y = player2Input.readInt();

                    if (player1Board[x][y] == OCCUPIED)//assumes the player is not targeting an already
                    //attacked tile
                    {
                        player1Board[x][y] = DESTROYED;
                        player2Output.writeInt(1);
                    } else {
                        player1Board[x][y] = MISS;
                        player2Output.writeInt(0);
                    }
                    
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
                    turnNumber++;
                }
            } catch (IOException e) {
                System.err.println("client-server connection failed");
            }
        }  
    }
}
