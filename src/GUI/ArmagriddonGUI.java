package GUI;

/**
 * Created by Emily on 3/5/2016.
 */

// import armagriddon.Armagriddon, state, logic, engine, etc.

import state.*;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.Dimension;
import java.awt.Color;


public class ArmagriddonGUI extends JFrame implements ActionListener{

    private LoginPanel loginPanel;
    private MainMenuPanel mainMenuPanel;
    private GamePlayPanel gamePlayPanel;

    private State state;

    public ArmagriddonGUI(State s) {
        reset(s);
    }

    public void reset(State s) {
        state = s;

        loginPanel = new LoginPanel(state);
        mainMenuPanel = new MainMenuPanel(state);
        gamePlayPanel = new GamePlayPanel(state, 8, 8); // state, gridRows, gridCols

        String title = "Armagriddon";
        setTitle(title);

        // Create main panel
        JPanel mainPane = new JPanel(new BorderLayout());
        mainPane.setPreferredSize(new Dimension(1024, 710));

        // Add panels - visibility initially set to false
        mainPane.add(loginPanel, BorderLayout.CENTER);
        mainPane.add(mainMenuPanel, BorderLayout.NORTH);
        mainPane.add(gamePlayPanel, BorderLayout.NORTH);

        // Set main window frame properties
        mainPane.setBackground(Color.white);
        setContentPane(mainPane);
        setVisible(true);
        setSize(getLayout().preferredLayoutSize(this));

        // Show in center of the screen
        setLocationRelativeTo(null);
        validate();
        repaint();
    }

    public void update() {
        repaint();
    }

    public void actionPerformed(ActionEvent evt) {
        Object source = evt.getSource();
    }

    public static void main(String[] args) {
        State s = new State();
        ArmagriddonGUI gui = new ArmagriddonGUI(s);
        gui.update();
    }
}


