package GUI;

import javafx.util.Pair;
import shared.ExecutionState;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Emily on 3/5/2016.
 * User provides username and password, the ActionListener
 * saves the input in the username and password variables, respectively
 *
 * After the login button is pressed, it sends the username and password
 * to the server (not yet, but it should), sets the ExecutionState for the
 * server to MAINMENU so the MainMenuPanel will show, and calls for the gui to be updated
 */
public class LoginPanel extends JPanel {

    private JPanel welcomePanel;
    private JPanel loginPanel;
    private JTextField usernameTextField;
    private JTextField passwordTextField;
    private String username;
    private String password;
    private JButton loginButton;

    public LoginPanel(ArmagriddonGUI gui) {
        usernameTextField = new JTextField(25);
        passwordTextField = new JTextField(25);
        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                username = usernameTextField.getText();
                password = passwordTextField.getText();
                sendUserInfo(); // create a new user in the server
                gui.setServerState(ExecutionState.MAIN_MENU); // now logged in, so move on to MainMenuPanel
                gui.update(); // show the MainMenuPanel
            }
        });

        welcomePanel = new JPanel(new GridBagLayout());
        welcomePanel.setPreferredSize(new Dimension(1000, 700));
        welcomePanel.setBackground(Color.WHITE);
        welcomePanel.setLayout(new GridBagLayout());

        loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setPreferredSize(new Dimension(200,200));
        loginPanel.setBorder(new TitledBorder("Login"));
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(new JLabel("Username:"), gbc);
        gbc.gridy++;
        loginPanel.add(new JLabel("Password:"), gbc);

        gbc.gridx++;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        loginPanel.add(usernameTextField, gbc);
        gbc.gridy++;
        loginPanel.add(passwordTextField, gbc);

        gbc.gridx = 1;
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        loginPanel.add(loginButton, gbc);

        GridBagConstraints gbcWelcome = new GridBagConstraints();
        gbcWelcome.gridx = 0;
        gbcWelcome.gridy = 0;
        welcomePanel.add(loginPanel);

        add(welcomePanel);
        setVisible(false);
    }

    public Pair<String, String> getUserInfo() {
        Pair<String, String> userInfo = new Pair<String, String>(username, password);
        return userInfo;
    }

    public void sendUserInfo() {
        // send user info to server
    }

}