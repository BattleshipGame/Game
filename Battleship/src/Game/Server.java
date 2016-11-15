package Game;

import static Game.BattleshipData.SHIP_COUNT;
import static Game.BattleshipData.tile.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 * Runs the server for the Battleship game
 * @author Maurice Ajluni
 */
public class Server extends JFrame implements BattleshipData {
//using enum interface for now    
    // public final int VACANT = 0;
    // public final int OCCUPIED = 1;
    //public final int DESTROYED = 2;

    // Text area for displaying contents
    private JTextArea jta = new JTextArea();

    public static void main(String[] args) {
        new Server();
    }

    public Server() {
        // Place text area on the frame
        setLayout(new BorderLayout());
        add(new JScrollPane(jta), BorderLayout.CENTER);
        setTitle("MultiThreadServer");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true); // It is necessary to show the frame here!

        int test = 1;
        try {
            // Create a server socket
            ServerSocket serverSocket = new ServerSocket(8000);
            jta.append("MultiThreadServer started at " + new Date() + '\n');

            // Number a client
            int clientNo = 1;

            while (true) {
                jta.append(new Date()
                        + ": Wait for players to join session " + clientNo + '\n');

                // Connect to player 1
                Socket player = serverSocket.accept();

                jta.append(new Date() + ": Player " + clientNo + " joined session " + clientNo + '\n');
                jta.append("Player " + clientNo + "'s IP address" + player.getInetAddress().getHostAddress() + '\n');
                // Display this session and increment session number
                jta.append(new Date() + ": Start a thread for session " + clientNo++ + '\n');

                // Create a new thread for this session of two players
                HandleAClient task = new HandleAClient(player, clientNo);

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

        private Socket player;
        private String playerName;

        //creates grids that store the status of each tile on both game boards
        private tile[][] playerBoard;
        private Ship[] playerShips;

        /**
         * Construct a thread
         */
        public HandleAClient(Socket s, int count) {
            this.player = s;
            playerBoard = new tile[SIDE_LENGTH][SIDE_LENGTH];
            playerShips = new Ship[SHIP_COUNT];
            
            playerName = "Player " + count;
            
            //initialize boards with empty tiles
            for (int ii = 0; ii < SIDE_LENGTH; ii++) {
                for (int jj = 0; jj < SIDE_LENGTH; jj++) {
                    playerBoard[ii][jj] = vacant;
                }
            }
        }

        /**
         * Run a thread
         */
        @Override
        public void run()
        {   
            try {
                // Create data input and output streams
                ObjectInputStream playerInput = new ObjectInputStream(player.getInputStream());
                ObjectOutputStream playerOutput = new ObjectOutputStream(player.getOutputStream());

                //ObjectInputStream player2Input = new ObjectInputStream(player2.getInputStream());
                //ObjectOutputStream player2Output = new ObjectOutputStream(player2.getOutputStream());

                /*
                //runs 5 turns of placing ships 
                for (int ii = 0; ii < SHIP_COUNT; ii++) {
                    //at current stage, adds a ship along a line using a coordinate, orientation, and 
                    //ship size
                    Point point = null;
                    try {
                        point = (Point) player1Input.readObject();
                        System.out.println(point.toString());
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    int orientation = player1Input.readInt();
                    int shipSize = player1Input.readInt();
                    Point[] pointArray = new Point[shipSize];//stores the points to create a ship
                    
                    //orientation of 1 is horizontal
                    if (orientation == 1) {
                        for (int jj = 0; jj < shipSize; jj++) {
                            player1Board[point.x + jj][point.y] = occupied; //makes each location
                            // along the line contain a ship tile
                            pointArray[jj] = new Point(point.x + jj, point.y);
                        }
                    }
                    player1Ships[ii] = new Ship(pointArray);

                }
                */

                //lets players take turns firing at each other; broken when a player wins
                while (true)
                {
                    //String playerName = 
                }

            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }
}
