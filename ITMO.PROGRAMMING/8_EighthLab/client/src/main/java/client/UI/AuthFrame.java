package client.UI;

import client.Main;
import client.utility.ClientCommandHandler;
import client.utility.NetworkHandler;
import shared.command.Login;
import shared.command.Register;
import shared.exceptions.ConnectionLostException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AuthFrame extends JFrame {

    // ==== Components for Login tab ====
    private JTextField loginUsernameField;
    private JPasswordField loginPasswordField;
    private JButton loginButton;

    // ==== Components for Register tab ====
    private JTextField registerUsernameField;
    private JPasswordField registerPasswordField;
    private JPasswordField registerConfirmPasswordField;
    private JButton registerButton;

    public AuthFrame() {
        setTitle("Authentication / Registration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null); // center on screen

        // Create a JTabbedPane with “Login” and “Register” tabs
        JTabbedPane tabbedPane = new JTabbedPane();

        // Create Login panel and add
        JPanel loginPanel = createLoginPanel();
        tabbedPane.addTab("Login", loginPanel);

        // Create Register panel and add
        JPanel registerPanel = createRegisterPanel();
        tabbedPane.addTab("Register", registerPanel);

        add(tabbedPane);
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username label + field
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Username:"), gbc);

        loginUsernameField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(loginUsernameField, gbc);

        // Password label + field
        gbc.gridy = 1;
        gbc.gridx = 0;
        panel.add(new JLabel("Password:"), gbc);

        loginPasswordField = new JPasswordField(20);
        gbc.gridx = 1;
        panel.add(loginPasswordField, gbc);

        // Login button
        loginButton = new JButton("Login");
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(loginButton, gbc);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = loginUsernameField.getText().trim();
                char[] passwordChars = loginPasswordField.getPassword();
                String password = new String(passwordChars);

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            AuthFrame.this,
                            "Please enter both username and password.",
                            "Missing Information",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }

                boolean authenticated = authenticate(username, password);

                if (authenticated) {
                    Main.openMainFrame(username);
                    AuthFrame.this.dispose();
                } else {
                    JOptionPane.showMessageDialog(
                            AuthFrame.this,
                            "Invalid username or password.",
                            "Authentication Failed",
                            JOptionPane.ERROR_MESSAGE
                    );
                }


                loginPasswordField.setText("");
            }
        });

        return panel;
    }

    private JPanel createRegisterPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Username:"), gbc);

        registerUsernameField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(registerUsernameField, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        panel.add(new JLabel("Password:"), gbc);

        registerPasswordField = new JPasswordField(20);
        gbc.gridx = 1;
        panel.add(registerPasswordField, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        panel.add(new JLabel("Confirm Password:"), gbc);

        registerConfirmPasswordField = new JPasswordField(20);
        gbc.gridx = 1;
        panel.add(registerConfirmPasswordField, gbc);

        registerButton = new JButton("Register");
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(registerButton, gbc);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = registerUsernameField.getText().trim();
                String password = new String(registerPasswordField.getPassword());
                String confirmPassword = new String(registerConfirmPasswordField.getPassword());

                if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            AuthFrame.this,
                            "Please fill in all fields.",
                            "Missing Information",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(
                            AuthFrame.this,
                            "Passwords do not match.",
                            "Validation Error",
                            JOptionPane.WARNING_MESSAGE
                    );
                    registerPasswordField.setText("");
                    registerConfirmPasswordField.setText("");
                    return;
                }

                // TODO: replace with real registration logic
                boolean registered = register(username, password);

                if (registered) {
                    JOptionPane.showMessageDialog(
                            AuthFrame.this,
                            "Registration successful! You can now log in.",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    // Automatically switch to Login tab
                    JTabbedPane tabs = (JTabbedPane) AuthFrame.this.getContentPane().getComponent(0);
                    tabs.setSelectedIndex(0);
                } else {
                    JOptionPane.showMessageDialog(
                            AuthFrame.this,
                            "Registration failed. Username may already exist.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }

                // Clear all fields
                registerUsernameField.setText("");
                registerPasswordField.setText("");
                registerConfirmPasswordField.setText("");
            }
        });

        return panel;
    }


    private boolean authenticate(String username, String password) {
        ClientCommandHandler cch = Main.invoker;
        try {
            cch.invoke(new Login(username, password));
            return cch.getFeedback().trim().equals("Вы успешно вошли в аккаунт.");
        } catch (Exception e) {
            return false;
        }
    }


    private boolean register(String username, String password) {
        ClientCommandHandler cch = Main.invoker;
        try {
            cch.invoke(new Register(username, password));
            return cch.getFeedback().trim().equals("Вы успешно зарегитрировались.");
        } catch (Exception e) {
            return false;
        }
    }
}