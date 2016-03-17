package GUI;

import shared.ExecutionState;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Paths;

/**
 * Created by Emily on 3/5/2016.
 * User provides username, the ActionListener
 * saves the input in the username variable
 *
 * After the login button is pressed, it sends the username
 * to the server (not yet, but it should), sets the ExecutionState for the
 * server to MAINMENU so the MainMenuPanel will show, and calls for the gui to be updated
 */
public class LoginPanel extends JPanel {

    private JPanel welcomePanel;
    private JPanel loginPanel;
    private JTextField usernameTextField;
    private String username;
    private JButton loginButton;
    private boolean nameReady;

    public LoginPanel(ArmagriddonGUI gui) {
    	nameReady = false;
        usernameTextField = new JTextField(25);
        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                username = usernameTextField.getText();
                nameReady = true;
                gui.setExecutionState(ExecutionState.MAIN_MENU); // now logged in, so move on to MainMenuPanel
                gui.update(); // show the MainMenuPanel
            }
        });

        setBackground(new Color(51,51,51));

        welcomePanel = new JPanel(new GridBagLayout());
        welcomePanel.setPreferredSize(new Dimension(710, 710));
        welcomePanel.setLayout(new GridBagLayout());
        welcomePanel.setBackground(new Color(51,51,51));

        loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setPreferredSize(new Dimension(200,200));
        loginPanel.setBorder(new TitledBorder("Login"));
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(new JLabel("Username:"), gbc);

        gbc.gridx++;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        loginPanel.add(usernameTextField, gbc);

        gbc.gridy++;
        loginPanel.add(new JLabel("    "), gbc);
        gbc.gridx = 1;
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        loginPanel.add(loginButton, gbc);

        GridBagConstraints gbcWelcome = new GridBagConstraints();
        String pathString = Paths.get("").toAbsolutePath().toString();
        gbcWelcome.gridx = 0;
        gbcWelcome.gridy = 0;
        welcomePanel.add(new JLabel(new ImageIcon(pathString+"/src/GUI/images/armagriddon.png")), gbcWelcome);
        gbcWelcome.gridy++;
        welcomePanel.add(loginPanel, gbcWelcome);

        add(welcomePanel);
    }

    public String getUsername() {
    	while(!nameReady) {
    		try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		//block
    	}
        return username;
    }

}