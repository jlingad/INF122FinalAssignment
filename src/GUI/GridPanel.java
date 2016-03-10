package GUI;

import javafx.util.Pair;
import state.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Emily on 3/5/2016.
 */
public class GridPanel extends JPanel implements MouseListener {

    private GameState state;
    private JPanel gridPanel;
    private int rows;
    private int cols;
    private JLabel numPanels[];

    public GridPanel(GameState s) {
        state = s;
        rows = state.getGridDimensions().getKey();
        cols = state.getGridDimensions().getValue();
        numPanels = new JLabel[rows*cols];

        gridPanel = new JPanel(new GridBagLayout());
        gridPanel.setPreferredSize(new Dimension(500,500));
        gridPanel.setBackground(Color.DARK_GRAY);
        gridPanel.setLayout(new GridLayout(rows, cols));

        for (int i=0; i<numPanels.length; i++) {
            numPanels[i] = new JLabel("", JLabel.CENTER);
            numPanels[i].setOpaque(true);
            numPanels[i].setBackground(Color.WHITE);
            numPanels[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            numPanels[i].addMouseListener(this);
            numPanels[i].setToolTipText(Integer.toString(i));
        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        for (int i=0; i<numPanels.length; i++) {
            gridPanel.add(numPanels[i]);
            gbc.gridy++;
        }

        add(gridPanel);
    }

    public void update() {

    }

    public JLabel[] getGrid() {
        return numPanels;
    }

    public void mouseClicked(MouseEvent e) {
        // instead of specifying the type of game piece (e.g. "black_checker" below),
        // this should read from the GameState the game that is being played and
        // the game piece associated with the current player. Perhaps the GameState
        // can store a pair or list of ImageIcons/game pieces for the players
        GamePieceGenerator gamePiece = new GamePieceGenerator("black_checker");
        JLabel clickedPanel = (JLabel) e.getSource();
        System.out.println(clickedPanel.getToolTipText()); // prints out the square that was selected
        // check to see if the move is valid. if it is, add the game piece to the grid
        // then send the grid to the server. Once the server receives the updated grid,
        // it should change the player turn
        if (clickedPanel.getBackground() == Color.WHITE) {
            clickedPanel.setIcon(gamePiece.getGamePiece());
        }
    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

}