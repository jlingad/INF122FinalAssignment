package GUI;

import server.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * Created by Emily on 3/5/2016.
 * Future edits: title border should say name of game being played
 */
public class GamePlayPanel extends JPanel {

    private ArmagriddonGUI gui;
    private GameState state;
    private GameLogic logic;
    private TurnPanel turnPanel;
    private ScorePanel scorePanel;
    private GridPanel gridPanel;

    private JPanel gamePanel;

    public GamePlayPanel(ArmagriddonGUI g, GameState s, GameLogic l) {
        gui = g;
        state = s;
        logic = l;

        turnPanel = new TurnPanel(state);
        gridPanel = new GridPanel(state, logic, this);
        scorePanel = new ScorePanel(state);

        gamePanel = new JPanel(new GridBagLayout());
        gamePanel.setPreferredSize(new Dimension(710, 710));
        gamePanel.setBorder(new TitledBorder(state.getGameName())); // edit to show name of game
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

    // updates the turn label in the turnPanel to indicate
    // the correct current player
    public void updateTurnLabel() {
        JLabel turnLabel = turnPanel.getTurnLabel();
        turnPanel.setTurnLabel(turnLabel);
    }

    public ArmagriddonGUI getGUI() {
        return gui;
    }

}
