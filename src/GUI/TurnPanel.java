package GUI;

import state.State;

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

    private State state;

    private JPanel turnPanel;
    private JLabel turnLabel;

    private Integer currentPlayer;

    public TurnPanel(State s) {
        state = s;
        currentPlayer = state.getCurrentPlayer();

        turnLabel = new JLabel();
        setTurnLabel();

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
        setTurnLabel();
    }

    private void setTurnLabel() {
        if (currentPlayer == 1)
            turnLabel.setText("It is Player 2's turn.");
        else if (currentPlayer == 2)
            turnLabel.setText("It is Player 1's turn.");
    }
}
