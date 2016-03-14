package GUI;

import server.*;
import shared.ExecutionState;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by Emily on 3/5/2016.
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

        setBackground(new Color(51,51,51));
        turnPanel = new TurnPanel(state);
        turnPanel.setBackground(new Color(51,51,51));
        gridPanel = new GridPanel(state, logic, this);
        scorePanel = new ScorePanel(state);
        scorePanel.setBackground(new Color(51,51,51));

        gamePanel = new JPanel(new GridBagLayout());
        gamePanel.setPreferredSize(new Dimension(710, 700));
        gamePanel.setBorder(new TitledBorder(state.getGameName()));
        gamePanel.setBorder(new TitledBorder(new LineBorder(Color.WHITE), state.getGameName(), TitledBorder.LEFT,
                TitledBorder.DEFAULT_POSITION, new Font ( "Arial", Font.BOLD, 14 ), Color.WHITE));
        gamePanel.setBackground(new Color(51,51,51));
        gamePanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gamePanel.add(turnPanel, gbc);
        gbc.gridy++;
        gamePanel.add(gridPanel, gbc);
        gbc.gridy++;
        gamePanel.add(scorePanel, gbc);

        // set the special game pieces
        if (logic.hasSpecialVersion()) {
            String pathString = Paths.get("").toAbsolutePath().toString();
            gbc.gridy++;
            JButton special = new JButton(new ImageIcon(pathString+"/src/GUI/images/jarjarbinks.gif"));
            special.setBorder(new EmptyBorder(10,10,10,10));
            special.setBackground(Color.WHITE);
            gamePanel.add(special, gbc);
            special.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    state.setSpecialGrid();
                }
            });
        }

        add(gamePanel);
        setVisible(false);
    }

    // updates the turn label in the turnPanel to indicate
    // the correct current player
    public void updateTurnLabel() {
        JLabel turnLabel = turnPanel.getTurnLabel();
        turnPanel.setTurnLabel(turnLabel);
    }

    public void updateScorePanel() {
        scorePanel.update();
    }

    public ArmagriddonGUI getGUI() {
        return gui;
    }

}
