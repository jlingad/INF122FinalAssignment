package GUI;

import server.GameState;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * Created by Emily on 3/5/2016.
 * Notes: Assuming State stores whose turn it is, Turn switches
 * the current player
 *  Does not yet update the GUI
 */
public class TurnPanel extends JPanel {

    private GameState state;

    private JPanel turnPanel;
    private JLabel turnLabel;


    public TurnPanel(GameState s) {
        state = s;

        turnLabel = new JLabel();
        setTurnLabel(turnLabel);

        turnPanel = new JPanel(new GridBagLayout());
        turnPanel.setPreferredSize(new Dimension(300,50));
        turnPanel.setBorder(new TitledBorder("Player Turn"));
        turnPanel.setBackground(Color.WHITE);
        turnPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        turnPanel.add(turnLabel);

        add(turnPanel);
    }

    public void update() {
    }

    public JLabel getTurnLabel() {
        return turnLabel;
    }

    public void setTurnLabel(JLabel label) {
        if (state.getCurrentPlayer() == 1)
            label.setText("It is Player 1's turn.");
        else if (state.getCurrentPlayer() == 2)
            label.setText("It is Player 2's turn.");
        repaint();
    }

}
