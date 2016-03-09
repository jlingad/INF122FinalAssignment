package GUI;

import state.State;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Emily on 3/5/2016.
 * Notes: move grid member variable and create function that returns the grid
 */
public class GridPanel extends JPanel implements MouseListener {
    private State state;

    private JPanel gridPanel;

    public GridPanel(State s, int rows, int cols) {
        state = s;

        gridPanel = new JPanel(new GridBagLayout());
        gridPanel.setPreferredSize(new Dimension(500,500));
        gridPanel.setBackground(Color.DARK_GRAY);
        gridPanel.setLayout(new GridLayout(rows, cols));

        JLabel numPanels[] = new JLabel[rows*cols];
        for (int i=0; i<numPanels.length; i++) {
            numPanels[i] = new JLabel("");
            numPanels[i].setOpaque(true);
            numPanels[i].setBackground(Color.WHITE);
            numPanels[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            numPanels[i].addMouseListener(this);
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

    public void mouseClicked(MouseEvent e) {
        JLabel clickedPanel = (JLabel) e.getSource();
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
