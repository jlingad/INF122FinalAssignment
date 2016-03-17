package GUI;

/**
 * Created by Emily on 3/5/2016.
 */

import server.*;
import server.GameState;
import shared.ExecutionState;
import shared.MessageType;
import shared.Protocol;
import state.*;

import javax.swing.*;
import java.awt.*;
import java.awt.Dimension;
import java.util.ArrayList;
import java.awt.Color;


public class ArmagriddonGUI extends JFrame{
	ArrayList<Protocol> outgoingMessages;
	
    //private ServerState serverState;
    private GameState gameState;
    private GameLogic gameLogic;
    private ExecutionState executionState;
    
    private JPanel mainPane;
    private LoginPanel loginPanel;
    private MainMenuPanel mainMenuPanel;
    private GamePlayPanel gamePlayPanel;

//    public ArmagriddonGUI(ServerState s) {
//        serverState = s;
//        reset();
//        // end the process when the jframe is closed
//        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//    }
    public ArmagriddonGUI(ExecutionState startState, ArrayList<Protocol> outgoingMessages) {
        executionState = startState;
        this.outgoingMessages = outgoingMessages;
        reset();
        // end the process when the jframe is closed
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void reset() {
        String title = "Armagriddon";
        setTitle(title);

        // Create main panel to put in frame
        mainPane = new JPanel(new BorderLayout());
        mainPane.setPreferredSize(new Dimension(710, 710));

        // the program is just started, the ExecutionState
        // should be at LOGIN, so create the LoginPanel
        if (executionState == ExecutionState.LOGIN) {
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
        if (executionState == ExecutionState.MAIN_MENU) {
            mainMenuPanel = new MainMenuPanel(this, loginPanel.getUsername());
            mainPane.add(mainMenuPanel, BorderLayout.CENTER);
            loginPanel.setVisible(false);
            mainMenuPanel.setVisible(true);
            if (gamePlayPanel != null)
                gamePlayPanel.setVisible(false);
        } else if (executionState == ExecutionState.GAMEPLAY) {
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
    	executionState = execState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void setGameLogic(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
    }

    public GUI.LoginPanel getLoginPanel()
    {
        return loginPanel;
    }
    
    public GUI.MainMenuPanel getMainMenuPanel()
    {
    	return mainMenuPanel;
    }
    
    public ExecutionState getExecutionState() {
    	return executionState;
    }
    
    public synchronized void processMessage (Protocol p) {
    	if(p.getMessageType() == MessageType.MOVE) {
    		gamePlayPanel.incomingMoveLogic(p.getClickedPanels());
    	}
    }
    
    public synchronized void queMessage (Protocol p) {
    	outgoingMessages.add(p);
    }
    
//    public static void main(String[] args) {
//        //ArmagriddonGUI gui = new ArmagriddonGUI(new ServerState());
//    	ArmagriddonGUI gui = new ArmagriddonGUI(ExecutionState.LOGIN);
//    }
}