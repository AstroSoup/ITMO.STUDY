package client.UI;

import client.Main;
import client.utility.ClientCommandHandler;
import client.utility.Localization;
import shared.command.Login;
import shared.command.Register;
import shared.exceptions.ConnectionLostException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AuthFrame extends JFrame {

    private JButton languageButton;
    private JPopupMenu languageMenu;

    private JTabbedPane tabbedPane;

    private JLabel loginUsernameLabel;
    private JTextField loginUsernameField;
    private JLabel loginPasswordLabel;
    private JPasswordField loginPasswordField;
    private JButton loginButton;

    private JLabel registerUsernameLabel;
    private JTextField registerUsernameField;
    private JLabel registerPasswordLabel;
    private JPasswordField registerPasswordField;
    private JLabel registerConfirmPasswordLabel;
    private JPasswordField registerConfirmPasswordField;
    private JButton registerButton;

    public AuthFrame() {
        super();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        tabbedPane = new JTabbedPane();
        add(tabbedPane, BorderLayout.CENTER);

        tabbedPane.addTab("", createLoginPanel());
        tabbedPane.addTab("", createRegisterPanel());

        JPanel bottomBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        languageButton = new JButton();
        languageButton.setFocusable(false);
        bottomBar.add(languageButton);

        languageMenu = new JPopupMenu();
        for (Localization.Language lang : Localization.Language.values()) {
            JMenuItem item = new JMenuItem(lang.getFlagEmoji() + "  " + lang.getDisplayName());
            item.addActionListener(ev -> {
                Localization.setLanguage(lang);
                updateTexts();
            });
            languageMenu.add(item);
        }
        languageButton.addActionListener(e -> languageMenu.show(languageButton, 0, languageButton.getHeight()));

        add(bottomBar, BorderLayout.SOUTH);

        updateTexts();
    }

    private void updateTexts() {
        setTitle(Localization.get("auth.title"));

        tabbedPane.setTitleAt(0, Localization.get("tab.login"));
        tabbedPane.setTitleAt(1, Localization.get("tab.register"));

        loginUsernameLabel.setText(Localization.get("label.username"));
        loginPasswordLabel.setText(Localization.get("label.password"));
        loginButton.setText(Localization.get("button.login"));

        registerUsernameLabel.setText(Localization.get("label.username"));
        registerPasswordLabel.setText(Localization.get("label.password"));
        registerConfirmPasswordLabel.setText(Localization.get("label.confirm_password"));
        registerButton.setText(Localization.get("button.register"));

        Localization.Language current = Localization.getLanguage();
        languageButton.setText(current.getFlagEmoji());

        SwingUtilities.updateComponentTreeUI(this);
    }


    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        loginUsernameLabel = new JLabel();
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(loginUsernameLabel, gbc);

        loginUsernameField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(loginUsernameField, gbc);

        loginPasswordLabel = new JLabel();
        gbc.gridy = 1;
        gbc.gridx = 0;
        panel.add(loginPasswordLabel, gbc);

        loginPasswordField = new JPasswordField(20);
        gbc.gridx = 1;
        panel.add(loginPasswordField, gbc);

        loginButton = new JButton();
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(loginButton, gbc);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = loginUsernameField.getText().trim();
                String password = new String(loginPasswordField.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            AuthFrame.this,
                            Localization.get("msg.enter_both"),
                            Localization.get("title.missing_info"),
                            JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }

                boolean authenticated = authenticate(username, password);
                if (authenticated) {
                    Main.openMainFrame(username, password);
                    AuthFrame.this.dispose();
                } else {
                    JOptionPane.showMessageDialog(
                            AuthFrame.this,
                            Localization.get("msg.invalid_credentials"),
                            Localization.get("title.auth_failed"),
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

        registerUsernameLabel = new JLabel();
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(registerUsernameLabel, gbc);

        registerUsernameField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(registerUsernameField, gbc);

        registerPasswordLabel = new JLabel();
        gbc.gridy = 1;
        gbc.gridx = 0;
        panel.add(registerPasswordLabel, gbc);

        registerPasswordField = new JPasswordField(20);
        gbc.gridx = 1;
        panel.add(registerPasswordField, gbc);

        registerConfirmPasswordLabel = new JLabel();
        gbc.gridy = 2;
        gbc.gridx = 0;
        panel.add(registerConfirmPasswordLabel, gbc);

        registerConfirmPasswordField = new JPasswordField(20);
        gbc.gridx = 1;
        panel.add(registerConfirmPasswordField, gbc);

        registerButton = new JButton();
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
                            Localization.get("msg.fill_all_fields"),
                            Localization.get("title.missing_info"),
                            JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(
                            AuthFrame.this,
                            Localization.get("msg.passwords_not_match"),
                            Localization.get("title.validation_error"),
                            JOptionPane.WARNING_MESSAGE
                    );
                    registerPasswordField.setText("");
                    registerConfirmPasswordField.setText("");
                    return;
                }

                boolean registered = register(username, password);
                if (registered) {
                    JOptionPane.showMessageDialog(
                            AuthFrame.this,
                            Localization.get("msg.registration_success"),
                            Localization.get("title.success"),
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    tabbedPane.setSelectedIndex(0);
                } else {
                    JOptionPane.showMessageDialog(
                            AuthFrame.this,
                            Localization.get("msg.registration_failed"),
                            Localization.get("title.error"),
                            JOptionPane.ERROR_MESSAGE
                    );
                }

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
            String fb = cch.getFeedback().trim();
            return fb.equals("Вы успешно вошли в аккаунт.");
        } catch (ConnectionLostException e) {
            return false;
        }
    }

    private boolean register(String username, String password) {
        ClientCommandHandler cch = Main.invoker;
        try {
            cch.invoke(new Register(username, password));
            String fb = cch.getFeedback().trim();
            return fb.equals("Вы успешно зарегитрировались.");
        } catch (ConnectionLostException e) {
            return false;
        }
    }
}
