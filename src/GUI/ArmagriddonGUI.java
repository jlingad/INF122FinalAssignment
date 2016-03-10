package GUI;

/**
 * Created by Emily on 3/5/2016.
 */

import shared.ExecutionState;
import state.*;

import javax.swing.*;
import java.awt.*;
import java.awt.Dimension;
import java.awt.Color;


public class ArmagriddonGUI extends JFrame{

    private ServerState serverState;
    private GameState gameState;

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
        mainPane.setBackground(new Color(54, 67, 68));
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
            mainMenuPanel = new MainMenuPanel(this, loginPanel.getUserInfo().getKey());
            mainPane.add(mainMenuPanel, BorderLayout.CENTER);
            loginPanel.setVisible(false);
            mainMenuPanel.setVisible(true);
//            gamePlayPanel.setVisible(false);
        } else if (serverState.execState == ExecutionState.GAMEPLAY) {
            gameState = mainMenuPanel.getCreatedGameState();
            gamePlayPanel = new GamePlayPanel(this, gameState);
            mainPane.add(gamePlayPanel, BorderLayout.CENTER);
            loginPanel.setVisible(false);
            mainMenuPanel.setVisible(false);
            gamePlayPanel.setVisible(true);
        } else {
            System.out.println("***ERROR*** Invalid ExecutionState");
        }
        repaint();
    }

    public void setServerState(ExecutionState execState) {
        serverState.execState = execState;
    }

    public static void main(String[] args) {
        ServerState serverState = new ServerState();
        ArmagriddonGUI gui = new ArmagriddonGUI(serverState);
    }
}


