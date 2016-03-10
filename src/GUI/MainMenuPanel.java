package GUI;

import server.BattleshipState;
import server.CheckersState;
import server.GameState;
import server.TicTacToeState;
import shared.ExecutionState;
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
 *
 * After one of the button is pressed, it creates a new GameState, instead
 * it should send a signal to the server telling it which game was selected
 * then the server signals back to the GUI to show the GamePlayPanel with
 * the correct game information (e.g. grid size)
 */
public class MainMenuPanel extends JPanel implements ActionListener {

    ArmagriddonGUI gui;

    private JPanel gameOptionsPanel;
    private JPanel checkersPanel;
    private JPanel tictactoePanel;
    private JPanel battleshipPanel;

    private JButton checkersButton;
    private JButton tictactoeButton;
    private JButton battleshipButton;

    JLabel selectGame;
    JLabel selectedGame;

    public MainMenuPanel(ArmagriddonGUI gui, String username) {
        this.gui = gui;

        setBackground(Color.WHITE);
        selectGame = new JLabel("Hi, " + username + "! Please select a game.");
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
        if (source == tictactoeButton) {
            GameState state = new TicTacToeState();
            gui.setGameState(state);
        }
        else if (source == checkersButton) {
            GameState state = new CheckersState();
            gui.setGameState(state);
        }
        else if (source == battleshipButton) {
            GameState state = new BattleshipState();
            gui.setGameState(state);
        }
        gui.setExecutionState(ExecutionState.GAMEPLAY); // game selected, so move onto GamePlayPanel
        gui.update(); // show the GamePlayPanel
    }

}