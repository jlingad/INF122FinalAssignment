package GUI;

/**
 * Created by Emily on 3/5/2016.
 */

import server.*;
import server.GameState;
import shared.ExecutionState;
import state.*;

import javax.swing.*;
import java.awt.*;
import java.awt.Dimension;
import java.awt.Color;


public class ArmagriddonGUI extends JFrame{

    private ServerState serverState;
    private GameState gameState;
    private GameLogic gameLogic;

    private JPanel mainPane;
    private LoginPanel loginPanel;
    private MainMenuPanel mainMenuPanel;
    private GamePlayPanel gamePlayPanel;

    public ArmagriddonGUI(ServerState s) {
        serverState = s;
        reset();
    }

    public void reset() {
        String title = "Armagriddon";
        setTitle(title);

        // Create main panel to put in frame
        mainPane = new JPanel(new BorderLayout());
        mainPane.setPreferredSize(new Dimension(710, 710));

        // the program is just started, the ExecutionState
        // should be at LOGIN, so create the LoginPanel
        if (serverState.execState == ExecutionState.LOGIN) {
            loginPanel = new LoginPanel(this);
            mainPane.add(loginPanel, BorderLayout.CENTER);
            loginPanel.setVisible(true);
        }

        // Set main window frame properties
        setContentPane(mainPane);
        setVisible(true);
        setSize(getLayout().preferredLayoutSize(this));

        // Show in center of the screen
        setLocationRelativeTo(null);
        validate();
        repaint();
    }

    public void update() {
        if (serverState.execState == ExecutionState.MAIN_MENU) {
            mainMenuPanel = new MainMenuPanel(this, loginPanel.getUsername());
            mainPane.add(mainMenuPanel, BorderLayout.CENTER);
            loginPanel.setVisible(false);
            mainMenuPanel.setVisible(true);
            if (gamePlayPanel != null)
                gamePlayPanel.setVisible(false);
        } else if (serverState.execState == ExecutionState.GAMEPLAY) {
            gamePlayPanel = new GamePlayPanel(this, gameState, gameLogic);
            mainPane.add(gamePlayPanel, BorderLayout.CENTER);
            loginPanel.setVisible(false);
            mainMenuPanel.setVisible(false);
            gamePlayPanel.setVisible(true);
        }
        else
        {
            System.out.println("***ERROR*** Invalid ExecutionState");
        }
        repaint();
    }

    public void setExecutionState(ExecutionState execState) {
        serverState.execState = execState;
    }

    public void setGameState(GameState gameState) {
        serverState.gameState = gameState;
        this.gameState = serverState.gameState;
    }

    public void setGameLogic(GameLogic gameLogic) {
        serverState.gameLogic = gameLogic;
        this.gameLogic = serverState.gameLogic;
    }
    
    public GUI.LoginPanel getLoginPanel()
    {
    	return loginPanel;
    }

    public static void main(String[] args) {
        ServerState serverState = new ServerState();
        ArmagriddonGUI gui = new ArmagriddonGUI(serverState);
    }
}