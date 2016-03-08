package GUI;

import state.State;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Emily on 3/5/2016.
 * Notes: User provides username and password, the ActionListener
 * saves the input in the username and password variables, respectively
 *  Currently does not save into the database
 */
public class LoginPanel extends JPanel {

    private State state;

    private JPanel welcomePanel;
    private JPanel loginPanel;
    private JTextField usernameTextField;
    private JTextField passwordTextField;
    private String username;
    private String password;
    private JButton loginButton;

    public LoginPanel(State s) {
        state = s;
        usernameTextField = new JTextField(25);
        passwordTextField = new JTextField(25);
        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                username = usernameTextField.getText();
                password = passwordTextField.getText();
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
        setVisible(false); // initially set to false
    }

}