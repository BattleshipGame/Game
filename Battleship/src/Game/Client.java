package Game;

import java.awt.Color;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/**
 * Runs the Client for the Battleship game
 *
 * @author Maurice Ajluni
 */
public class Client extends javax.swing.JFrame implements BattleshipData, Runnable {

    private int[][] playerBoard;
    private DataOutputStream toServer;
    private DataInputStream fromServer;
    private Ship[] shipList;
    private Point target;
    private boolean ready = false;
    private boolean myTurn;
    private boolean isPlacementPhase;
    private int orientation = VERTICAL;
    private int selectedX, selectedY, length;
    private int player;
    private boolean playing;

    /**
     * Creates new form Client
     */
    public Client() {
        playerBoard = new int[SIDE_LENGTH][SIDE_LENGTH];
        shipList = new Ship[SHIP_COUNT];
        initComponents();

        //Atempts to connect to Server and start Game
        try {
            Socket socket = new Socket("localhost", 8000);
            fromServer = new DataInputStream(socket.getInputStream());
            toServer = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            System.err.println(ex.toString() + '\n');
        }

        Thread thread = new Thread(this);
        thread.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        opponentPanel = new javax.swing.JPanel();
        opponentLabel = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        opponentTable = new javax.swing.JTable();
        playerPanel = new javax.swing.JPanel();
        playerLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        playerTable = new javax.swing.JTable();
        outputPanel = new javax.swing.JPanel();
        targetLabel = new javax.swing.JLabel();
        fireButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        systemOutput = new javax.swing.JTextArea();
        targetLocation = new javax.swing.JLabel();
        readyButton = new javax.swing.JButton();
        placeButton = new javax.swing.JButton();
        verticalCheckBox = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Battleship");
        setName("window"); // NOI18N
        setResizable(false);

        opponentPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        opponentPanel.setName("opponentPanel"); // NOI18N
        opponentPanel.setPreferredSize(new java.awt.Dimension(500, 500));

        opponentLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        opponentLabel.setText("Opponent");

        opponentTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"1", null, null, null, null, null, null, null, null, null, null},
                {"2", null, null, null, null, null, null, null, null, null, null},
                {"3", null, null, null, null, null, null, null, null, null, null},
                {"4", null, null, null, null, null, null, null, null, null, null},
                {"5", null, null, null, null, null, null, null, null, null, null},
                {"6", null, null, null, null, null, null, null, null, null, null},
                {"7", null, null, null, null, null, null, null, null, null, null},
                {"8", null, null, null, null, null, null, null, null, null, null},
                {"9", null, null, null, null, null, null, null, null, null, null},
                {"10", null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        opponentTable.setCellSelectionEnabled(true);
        opponentTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        opponentTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                opponentTableMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(opponentTable);

        javax.swing.GroupLayout opponentPanelLayout = new javax.swing.GroupLayout(opponentPanel);
        opponentPanel.setLayout(opponentPanelLayout);
        opponentPanelLayout.setHorizontalGroup(
            opponentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(opponentPanelLayout.createSequentialGroup()
                .addGap(124, 124, 124)
                .addComponent(opponentLabel)
                .addContainerGap(125, Short.MAX_VALUE))
            .addGroup(opponentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        opponentPanelLayout.setVerticalGroup(
            opponentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(opponentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(opponentLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        opponentLabel.getAccessibleContext().setAccessibleName("playerName");

        playerPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        playerPanel.setName("opponentPanel"); // NOI18N
        playerPanel.setPreferredSize(new java.awt.Dimension(500, 500));

        playerLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        playerLabel.setText("Player");

        playerTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"1", null, null, null, null, null, null, null, null, null, null},
                {"2", null, null, null, null, null, null, null, null, null, null},
                {"3", null, null, null, null, null, null, null, null, null, null},
                {"4", null, null, null, null, null, null, null, null, null, null},
                {"5", null, null, null, null, null, null, null, null, null, null},
                {"6", null, null, null, null, null, null, null, null, null, null},
                {"7", null, null, null, null, null, null, null, null, null, null},
                {"8", null, null, null, null, null, null, null, null, null, null},
                {"9", null, null, null, null, null, null, null, null, null, null},
                {"10", null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        playerTable.setCellSelectionEnabled(true);
        playerTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(playerTable);

        javax.swing.GroupLayout playerPanelLayout = new javax.swing.GroupLayout(playerPanel);
        playerPanel.setLayout(playerPanelLayout);
        playerPanelLayout.setHorizontalGroup(
            playerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(playerPanelLayout.createSequentialGroup()
                .addGap(135, 135, 135)
                .addComponent(playerLabel)
                .addContainerGap(134, Short.MAX_VALUE))
            .addGroup(playerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        playerPanelLayout.setVerticalGroup(
            playerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(playerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(playerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        outputPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        targetLabel.setText("Target:");

        fireButton.setText("FIRE");
        fireButton.setEnabled(false);
        fireButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fireButtonMouseClicked(evt);
            }
        });

        systemOutput.setColumns(20);
        systemOutput.setRows(5);
        jScrollPane2.setViewportView(systemOutput);

        targetLocation.setText("-,-");

        readyButton.setText("Ready");
        readyButton.setEnabled(false);
        readyButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                readyButtonMouseClicked(evt);
            }
        });

        placeButton.setText("Place");
        placeButton.setEnabled(false);
        placeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                placeButtonActionPerformed(evt);
            }
        });

        verticalCheckBox.setSelected(true);
        verticalCheckBox.setText("Vertical");
        verticalCheckBox.setEnabled(false);
        verticalCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verticalCheckBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout outputPanelLayout = new javax.swing.GroupLayout(outputPanel);
        outputPanel.setLayout(outputPanelLayout);
        outputPanelLayout.setHorizontalGroup(
            outputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(outputPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(outputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(outputPanelLayout.createSequentialGroup()
                        .addComponent(targetLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(targetLocation)
                        .addGap(55, 55, 55)
                        .addComponent(fireButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(verticalCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(placeButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(readyButton)))
                .addContainerGap())
        );
        outputPanelLayout.setVerticalGroup(
            outputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(outputPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(outputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(outputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(fireButton)
                        .addComponent(readyButton)
                        .addComponent(placeButton)
                        .addComponent(verticalCheckBox))
                    .addGroup(outputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(targetLabel)
                        .addComponent(targetLocation)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(outputPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(opponentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(playerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(playerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                    .addComponent(opponentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(outputPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        opponentPanel.getAccessibleContext().setAccessibleName("opponentPanel");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //Tells the server this players ready status
    private void readyButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_readyButtonMouseClicked
        try {
            ready = true;
            toServer.writeBoolean(ready);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_readyButtonMouseClicked

    //Sends to the Server the locaiton at which was shot by this player
    private void fireButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fireButtonMouseClicked
        if (!isPlacementPhase)//redundant check for proper phase, no real reason to remove
        {
            try {
                fireButton.setEnabled(false);
                // toServer.write(target);
                toServer.writeInt(selectedX);
                toServer.writeInt(selectedY);
                toServer.flush();
                myTurn = false;
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_fireButtonMouseClicked

    //Checks if the placement of the ships is to be veritcal or horizontal
    private void verticalCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verticalCheckBoxActionPerformed
        // TODO add your handling code here:
        if (orientation == VERTICAL)//switches between vertical and horizontal ship placement
        {
            orientation = HORIZONTAL;
        } else {
            orientation = VERTICAL;
        }
    }//GEN-LAST:event_verticalCheckBoxActionPerformed

    //Gets the coordinates from the opponenetTable's selected cell
    private void opponentTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_opponentTableMouseClicked
        selectedX = opponentTable.getSelectedColumn();
        selectedY = opponentTable.getSelectedRow() + 1;
        targetLocation.setText(selectedX + ", " + selectedY);
    }//GEN-LAST:event_opponentTableMouseClicked

    //Tells Server that the ship has been placed at the selected location of the playerTable
    private void placeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_placeButtonActionPerformed
        if (isPlacementPhase) {
            try {
                switch (verifyPlacement()) {
                    case 1:
                        toServer.writeInt(playerTable.getSelectedColumn() - 1);
                        toServer.writeInt(playerTable.getSelectedRow());
                        toServer.writeInt(orientation);
                        toServer.writeInt(length);
                        break;
                    case 2:
                        systemOutput.append("Cannot place over another ship, try again");
                    case 3:
                        systemOutput.append("Would place the ship out of bounds, try again");
                }
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
    }//GEN-LAST:event_placeButtonActionPerformed
    }

    //performs logic for entire placement phase
    private void placeShips() throws IOException {
        //enables placement based components and disables firing components
        fireButton.setEnabled(false);
        opponentTable.setEnabled(false);
        placeButton.setEnabled(true);
        playerTable.setEnabled(true);
        for (int i = 0; i < shipList.length; i++) {
            fromServer.read();//recieves signal to begin each placement
            switch (i)//determines which ship to place, currently going from smallest to largest
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
            try {
                waitForMove();//stalls until move is sent
            } catch (Exception e)//does nothing over trivial exception case
            {
            }

        }

        //inverse of method start
        isPlacementPhase = false;
        placeButton.setEnabled(false);
        playerTable.setEnabled(false);

        fireButton.setEnabled(true);
        opponentTable.setEnabled(true);

    }

    //places a single ship into the int array for tracking
    public void placeShip() {
        if (orientation == HORIZONTAL) {
            for (int jj = 0; jj < length; jj++)//iterates through each point along the attempted placement's line
            {
                playerBoard[selectedX + jj][selectedY] = OCCUPIED;

            }
        } else {
            for (int jj = 0; jj < length; jj++)//iterates through each point along the attempted placement's line
            {
                playerBoard[selectedX][selectedY + jj] = OCCUPIED;
            }
        }
    }

    /**
     * @param x
     * @param y
     * @param size
     * @return The validity of the attempted placement. 1 is valid, 2 is failed
     * due to collision with another ship, and 3 if the attempted placement will
     * put the ship out of bounds.
     */
    private int verifyPlacement() {
        int result = 1;

        try {
            if (orientation == HORIZONTAL) {
                for (int jj = 0; jj < length; jj++)//iterates through each point along the attempted placement's line
                {
                    if (playerBoard[selectedX + jj][selectedY] == OCCUPIED) {
                        result = 2;
                    }
                }
            } else {
                for (int jj = 0; jj < length; jj++)//iterates through each point along the attempted placement's line
                {
                    if (playerBoard[selectedX][selectedY + jj] == OCCUPIED) {
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
        Client client = new Client();
        client.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton fireButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel opponentLabel;
    private javax.swing.JPanel opponentPanel;
    private javax.swing.JTable opponentTable;
    private javax.swing.JPanel outputPanel;
    private javax.swing.JButton placeButton;
    private javax.swing.JLabel playerLabel;
    private javax.swing.JPanel playerPanel;
    private javax.swing.JTable playerTable;
    private javax.swing.JButton readyButton;
    private javax.swing.JTextArea systemOutput;
    private javax.swing.JLabel targetLabel;
    private javax.swing.JLabel targetLocation;
    private javax.swing.JCheckBox verticalCheckBox;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
        try {
            isPlacementPhase = true;
            systemOutput.setText("Waiting for other player to connect");
            placeShips();

            while (playing) {
                if (player == 1)//TODO asign player value on connection
                {
                    waitForMove();
                    getAttackResult();
                    receiveStatus();
                } else {
                    receiveStatus();
                    waitForMove();
                    getAttackResult();
                }

            }

        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {

        }

    }

    //loops until the fire or place button is pressed
    @SuppressWarnings("SleepWhileInLoop")
    public void waitForMove() throws InterruptedException {
        while (myTurn) {
            Thread.sleep(50);
        }
        myTurn = true;
    }

    //recieves coords of opponent's attack to update GUI
    public void receiveStatus() throws IOException {
        int status = fromServer.readInt();

        switch (status) {
            case 1://TODO when either player wins
                playing = false;
                if (player == 1) {
                    systemOutput.append("You Win!");
                } else {
                    systemOutput.append("You Lose.");
                }
                break;

            case 2:
                playing = false;
                if (player == 2) {
                    systemOutput.append("You Win!");
                } else {
                    systemOutput.append("You Lose.");
                }
                break;
            default:
                receiveAttack();
                break;

        }
        myTurn = true;
    }

    private void receiveAttack() throws IOException {
        int x = fromServer.readInt();
        int y = fromServer.readInt();
        JLabel l = (JLabel) playerTable.findComponentAt(x, y);
        
        switch (playerBoard[x][y]) {
            case EMPTY:
                l.setBackground(Color.blue);
                systemOutput.append("Other player missed");
                break;
            case OCCUPIED:
                systemOutput.append("Other player landed a hit");
                l.setBackground(Color.red);

        }

    }

    private void getAttackResult() throws IOException {
        JLabel l = (JLabel) playerTable.findComponentAt(selectedX, selectedY);
        int result = fromServer.readInt();

        switch (result) {
            case 0:
                l.setBackground(Color.blue);
                systemOutput.append("\nMiss at " + selectedX + "," + selectedY + "\nNext player's turn.");
                break;
            case 1:
                systemOutput.append("\nHit at " + target + "\nNext player's turn.");
                l.setBackground(Color.red);
                break;
        }

    }
}
