package GUI;

import server.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Emily on 3/5/2016.
 */
public class GridPanel extends JPanel implements MouseListener {

    private GameLogic logic;
    private GameState state;
    private GamePlayPanel gamePlayPanel;
    private JPanel gridPanel;
    private int rows;
    private int cols;
    private JLabel numPanels[];

    public GridPanel(GameState s, GameLogic l, GamePlayPanel gamePlayPanel) {
        state = s;
        logic = l;
        this.gamePlayPanel = gamePlayPanel;
        rows = state.getGridDimensions().getKey();
        cols = state.getGridDimensions().getValue();
        numPanels = new JLabel[rows*cols];

        setBackground(Color.WHITE);
        gridPanel = new JPanel(new GridBagLayout());
        gridPanel.setPreferredSize(new Dimension(500,500));
        gridPanel.setBackground(Color.WHITE);
        gridPanel.setLayout(new GridLayout(rows, cols));

        // create grid/board
        for (int i=0; i<numPanels.length; i++) {
            numPanels[i] = new JLabel("", JLabel.CENTER);
            numPanels[i].setOpaque(true);
            numPanels[i].setBackground(Color.WHITE);
            numPanels[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            numPanels[i].addMouseListener(this);
            numPanels[i].setToolTipText(Integer.toString(i));
        }

        // add grid to GameState object
        state.setGrid(numPanels);

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
        // read which button label was clicked on
        JLabel clickedPanel = (JLabel) e.getSource();
        // change - GridPanel should somehow have access to Logic
        if (logic.isValidClick(state, clickedPanel))
            state.addClickedPanel(clickedPanel);
        if (state.getClickedPanels().size() == logic.getMaxClicks()) {
            // makeMove checks to see if there is a winner, if the move
            // is valid, then adds the piece to the board
            logic.makeMove(state, gamePlayPanel);
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