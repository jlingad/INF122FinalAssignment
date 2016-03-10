package GUI;

/**
 * Created by Emily on 3/5/2016.
 * Notes: Panels do not update
 */

import server.GameState;
import state.*;

import java.util.*;
import java.awt.*;
import java.awt.Dimension;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.Color;

public class ScorePanel extends JPanel{
    private GameState state;

    private JPanel scoresPanel;
    private JLabel player1Label;
    private JLabel player2Label;

    private HashMap<Integer, Integer> scoresMap;
    private Integer player;

    public ScorePanel(GameState s) {
        state = s;
        player = state.getCurrentPlayer();

        scoresMap = new HashMap<Integer, Integer>();
        scoresMap = s.getScores();

        player1Label = new JLabel("Player 1: " + scoresMap.get(1));
        player2Label = new JLabel("Player 2: " + scoresMap.get(2));

        scoresPanel = new JPanel(new GridBagLayout());
        scoresPanel.setPreferredSize(new Dimension(300,50));
        scoresPanel.setBorder(new TitledBorder("Scoreboard"));
        scoresPanel.setBackground(Color.WHITE);
        scoresPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        scoresPanel.add(player1Label, gbc);
        gbc.gridy++;
        scoresPanel.add(player2Label, gbc);

        add(scoresPanel);
    }

    public void update() {
        int oldScore = scoresMap.get(player);
        scoresMap.replace(player, oldScore, oldScore++);
        if (player == 1)
            player1Label.setText("Player 1: " + scoresMap.get(1));
        else if (player == 2)
            player2Label.setText("Player 2: " + scoresMap.get(2));
    }

}
