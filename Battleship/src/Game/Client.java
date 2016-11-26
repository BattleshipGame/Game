package Game;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.net.*;
import javax.swing.ButtonGroup;
import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 * Runs the Client for the Battleship game
 *
 * @author Maurice Ajluni
 */
public class Client extends JApplet implements BattleshipData {

    private int[][] playerBoard;
    private Cell[][] ownBoard = new Cell[SIDE_LENGTH][SIDE_LENGTH], opponentBoard = new Cell[SIDE_LENGTH][SIDE_LENGTH];
    private DataOutputStream toServer;
    private DataInputStream fromServer;
    private String playerName;
    private Ship[] shipList;
    private Point target;
    private boolean ready = false;
    private boolean isPlacementPhase;
    private int orientation = VERTICAL;
    private int selectedX, selectedY;
    private JButton fireButton;

    /**
     * Creates new form Client
     */
    public Client() {
        playerBoard = new int[SIDE_LENGTH][SIDE_LENGTH];
        shipList = new Ship[SHIP_COUNT];
        initComponents();
        fireButton.setEnabled(false);
        addButtons();

        try {
            Socket socket = new Socket("localhost", 8000);
            fromServer = new DataInputStream(socket.getInputStream());
            toServer = new DataOutputStream(socket.getOutputStream());

            placeShips();
        } catch (IOException ex) {
            System.err.println(ex.toString() + '\n');
        }

    }

    
   
                         
    private void initComponents() {

        /*setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Battleship");
        setName("window"); // NOI18N
        setResizable(false);*/

       
    }                       

    private void placeShips() throws IOException {
        
        fromServer.read();//recieves signal to begin each placement
        
        int length = 0;

        //TODO Somehow obtain coordinates and length from player's placement board 
        for (int i = 0; i < shipList.length; i++) {

            switch(i)//determines which ship to place, currently going from smallest to largest
            {
                case 0: 
                    length = 2;
                    systemOutput.setText("Placing Patrol Boat: Size 2");
                    break;
                    
                    case 1: 
                    length = 3;
                    systemOutput.setText("Placing Submarine #1: Size 3");
                    break;
                    
                    case 2: 
                    length = 3;
                    systemOutput.setText("Placing Submarine #2: Size 3");
                    break;
                    
                    case 3: 
                    length = 4;
                    systemOutput.setText("Placing Battleship: Size 4");
                    break;
                    
                    case 4: 
                    length = 5;
                    systemOutput.setText("Placing Carrier: Size 5");
                    break;                   
            }
            
            
            //sends details of verified placement to server 
            toServer.writeInt(selectedX);
            toServer.writeInt(selectedY);
            toServer.writeInt(orientation);
            toServer.writeInt(length);
            
            if(i >= SHIP_COUNT)//signals end of placement phase after all ships are placed
            {
                isPlacementPhase = false;
            }
        }
    }

    /**
     *
     * @param x
     * @param y
     * @param size
     * @return The validity of the attempted placement. 1 is valid, 2 is failed
     * due to collision with another ship, and 3 if the attempted placement will
     * put the ship out of bounds.
     */
    private int verifyPlacement(int x, int y, int size) {
        int result = 1;

        try {
            if (orientation == HORIZONTAL) {
                for (int jj = 0; jj < size; jj++)//iterates through each point along the attempted placement's line
                {
                    if (playerBoard[x + jj][y] == OCCUPIED) {
                        result = 2;
                    }
                }
            } else {
                for (int jj = 0; jj < size; jj++)//iterates through each point along the attempted placement's line
                {
                    if (playerBoard[x][y + jj] == OCCUPIED) {
                        result = 2;
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            result = 3;
        }
        return result;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        new Client().setVisible(true);
    }

    private static class Cell extends JPanel {
        public int x, y, status;
        

        public Cell(int x, int y)
        {
            this.x = x;
            this.y = y;
            setBorder(new LineBorder(Color.black, 1)); // Set cell's border
            addMouseListener(new ClickListener());  // Register listener
        }
    }

    private static class ClickListener implements MouseListener {

        public ClickListener() {
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mousePressed(MouseEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseExited(MouseEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    // Variables declaration - do not modify                     
    // End of variables declaration                   
}
