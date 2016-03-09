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
            numPanels[i] = new JLabel("");
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
        JLabel clickedPanel = (JLabel) e.getSource();
        System.out.println(clickedPanel.getToolTipText());
        if (clickedPanel.getBackground() == Color.WHITE)
            clickedPanel.setBackground(Color.BLACK);
        else
            clickedPanel.setBackground(Color.WHITE);
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