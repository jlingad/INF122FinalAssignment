package GUI;

import server.GameState;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * Created by Emily on 3/5/2016.
 * Future edits: title border should say name of game being played
 */
public class GamePlayPanel extends JPanel {

    private GameState state;

    private TurnPanel turnPanel;
    private ScorePanel scorePanel;
    private GridPanel gridPanel;

    private JPanel gamePanel;

    public GamePlayPanel(ArmagriddonGUI gui, GameState s) {
        state = s;

        turnPanel = new TurnPanel(state);
        gridPanel = new GridPanel(state);
        scorePanel = new ScorePanel(state);

        gamePanel = new JPanel(new GridBagLayout());
        gamePanel.setPreferredSize(new Dimension(1000, 680));
        gamePanel.setBorder(new TitledBorder("Checkers")); // edit to show name of game
        gamePanel.setBackground(Color.WHITE);
        gamePanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gamePanel.add(turnPanel, gbc);
        gbc.gridy++;
        gamePanel.add(gridPanel, gbc);
        gbc.gridy++;
        gamePanel.add(scorePanel, gbc);

        add(gamePanel);
        setVisible(false);
    }

}
