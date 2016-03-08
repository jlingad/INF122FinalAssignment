package GUI;

import state.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Paths;

/**
 * Created by Emily on 3/6/2016.
 * Notes: each game creates its own JPanel component - might be
 * better to use a map of games and their icon/button, rulebook, etc.
 */
public class MainMenuPanel extends JPanel implements ActionListener {

    private State state;

    private JPanel gameOptionsPanel;
    private JPanel checkersPanel;
    private JPanel tictactoePanel;
    private JPanel battleshipPanel;

    private JButton checkersButton;
    private JButton tictactoeButton;
    private JButton battleshipButton;

    JLabel selectGame;
    JLabel selectedGame;

    public MainMenuPanel(State s) {
        state = s;
        setBackground(Color.WHITE);
        selectGame = new JLabel("Please select a game.");
        selectedGame = new JLabel();

        String pathString = Paths.get("").toAbsolutePath().toString();

        gameOptionsPanel = new JPanel(new GridBagLayout());
        gameOptionsPanel.setPreferredSize(new Dimension(1000, 700));
        gameOptionsPanel.setBackground(Color.WHITE);
        gameOptionsPanel.setLayout(new GridBagLayout());

        tictactoePanel = new JPanel(new GridBagLayout());
        tictactoePanel.setPreferredSize(new Dimension(350, 200));
        tictactoePanel.setBackground(Color.WHITE);
        tictactoePanel.setLayout(new GridBagLayout());
        tictactoeButton = new JButton(
                new ImageIcon(pathString+"/src/GUI/images/tic-tac-toe.png"));
        tictactoeButton.setBackground(Color.WHITE);
        tictactoeButton.addActionListener(this);
        tictactoePanel.add(tictactoeButton);

        checkersPanel = new JPanel(new GridBagLayout());
        checkersPanel.setPreferredSize(new Dimension(350, 200));
        checkersPanel.setBackground(Color.WHITE);
        checkersPanel.setLayout(new GridBagLayout());
        checkersButton = new JButton(
                new ImageIcon(pathString+"/src/GUI/images/checkers.png"));
        checkersButton.setBackground(Color.WHITE);
        checkersButton.addActionListener(this);
        checkersPanel.add(checkersButton);

        battleshipPanel = new JPanel(new GridBagLayout());
        battleshipPanel.setPreferredSize(new Dimension(350, 200));
        battleshipPanel.setBackground(Color.WHITE);
        battleshipPanel.setLayout(new GridBagLayout());
        battleshipButton = new JButton(
                new ImageIcon(pathString+"/src/GUI/images/battleship.png"));
        battleshipButton.setBackground(Color.WHITE);
        battleshipButton.addActionListener(this);
        battleshipPanel.add(battleshipButton);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gameOptionsPanel.add(selectGame, gbc);
        gbc.gridy++;
        gameOptionsPanel.add(tictactoePanel, gbc);
        gbc.gridy++;
        gameOptionsPanel.add(checkersPanel, gbc);
        gbc.gridy++;
        gameOptionsPanel.add(battleshipPanel, gbc);
        gbc.gridy++;
        gameOptionsPanel.add(selectedGame, gbc);

        add(gameOptionsPanel);
        setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == tictactoeButton)
            selectedGame.setText("You have selected to play Tic_Tac-Toe");
        else if (source == checkersButton)
            selectedGame.setText("You have selected to play Checkers");
        else if (source == battleshipButton)
            selectedGame.setText("You have selected to play Battleship");
    }

}