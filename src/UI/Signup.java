package UI;

import logic.Client;
import logic.FileFunctions;
import logic.Person;
import logic.ServiceProvider;

import javax.swing.*;
import java.awt.*;

public class Signup {

    public Signup() {
        main(null);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("EmployInverse - Sign Up");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(900, 750));

        // Main panel with modern background
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(new Color(245, 247, 250));

        // Signup card panel
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(null);
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setPreferredSize(new Dimension(500, 680));
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 225, 230), 1),
            BorderFactory.createEmptyBorder(40, 60, 40, 60)
        ));

        // Title
        JLabel titleLabel = new JLabel("EmployInverse");
        titleLabel.setBounds(0, 20, 480, 40);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(new Color(30, 41, 59));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        cardPanel.add(titleLabel);

        JLabel subtitleLabel = new JLabel("Create your account");
        subtitleLabel.setBounds(10, 65, 480, 20);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(100, 116, 139));
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        cardPanel.add(subtitleLabel);

        // Name
        JLabel nameLabel = new JLabel("Name");
        nameLabel.setBounds(20, 110, 380, 20);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        nameLabel.setForeground(new Color(51, 65, 85));
        cardPanel.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setBounds(20, 135, 460, 42);
        nameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        nameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(203, 213, 225), 1),
            BorderFactory.createEmptyBorder(5, 12, 5, 12)
        ));
        cardPanel.add(nameField);

        // Phone
        JLabel phoneLabel = new JLabel("Phone");
        phoneLabel.setBounds(20, 190, 380, 20);
        phoneLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        phoneLabel.setForeground(new Color(51, 65, 85));
        cardPanel.add(phoneLabel);

        JTextField phoneField = new JTextField();
        phoneField.setBounds(20, 215, 460, 42);
        phoneField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        phoneField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(203, 213, 225), 1),
            BorderFactory.createEmptyBorder(5, 12, 5, 12)
        ));
        cardPanel.add(phoneField);

        // Email
        JLabel emailLabel = new JLabel("Email");
        emailLabel.setBounds(20, 270, 380, 20);
        emailLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        emailLabel.setForeground(new Color(51, 65, 85));
        cardPanel.add(emailLabel);

        JTextField emailField = new JTextField();
        emailField.setBounds(20, 295, 460, 42);
        emailField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        emailField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(203, 213, 225), 1),
            BorderFactory.createEmptyBorder(5, 12, 5, 12)
        ));
        cardPanel.add(emailField);

        // User name
        JLabel userNameLabel = new JLabel("Username");
        userNameLabel.setBounds(20, 350, 380, 20);
        userNameLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        userNameLabel.setForeground(new Color(51, 65, 85));
        cardPanel.add(userNameLabel);

        JTextField userNameField = new JTextField();
        userNameField.setBounds(20, 375, 460, 42);
        userNameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        userNameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(203, 213, 225), 1),
            BorderFactory.createEmptyBorder(5, 12, 5, 12)
        ));
        cardPanel.add(userNameField);

        // Password
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(20, 430, 380, 20);
        passwordLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        passwordLabel.setForeground(new Color(51, 65, 85));
        cardPanel.add(passwordLabel);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(20, 455, 460, 42);
        passField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(203, 213, 225), 1),
            BorderFactory.createEmptyBorder(5, 12, 5, 12)
        ));
        cardPanel.add(passField);

        // Account type
        JLabel accountTypeLabel = new JLabel("Account Type");
        accountTypeLabel.setBounds(20, 510, 380, 20);
        accountTypeLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        accountTypeLabel.setForeground(new Color(51, 65, 85));
        cardPanel.add(accountTypeLabel);

        JRadioButton isClient = new JRadioButton("Client");
        isClient.setBounds(20, 535, 180, 30);
        isClient.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        isClient.setBackground(Color.WHITE);
        isClient.setFocusPainted(false);

        JRadioButton isProvider = new JRadioButton("Service Provider");
        isProvider.setBounds(200, 535, 190, 30);
        isProvider.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        isProvider.setBackground(Color.WHITE);
        isProvider.setFocusPainted(false);

        ButtonGroup accountTypeGroup = new ButtonGroup();
        accountTypeGroup.add(isClient);
        accountTypeGroup.add(isProvider);
        cardPanel.add(isClient);
        cardPanel.add(isProvider);

        // Register Button
        JButton registerBtn = new JButton("Sign Up");
        registerBtn.setBounds(20, 580, 460, 45);
        registerBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setBackground(new Color(59, 130, 246));
        registerBtn.setBorder(BorderFactory.createEmptyBorder());
        registerBtn.setFocusPainted(false);
        registerBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cardPanel.add(registerBtn);

        // Hover effect for register button
        registerBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                registerBtn.setBackground(new Color(37, 99, 235));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                registerBtn.setBackground(new Color(59, 130, 246));
            }
        });

        registerBtn.addActionListener(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
            String userName = userNameField.getText();
            String pass = new String(passField.getPassword()).isEmpty() ? "Not mentioned" : new String(passField.getPassword());
            String isClientType = isClient.isSelected() ? "Yes" : (isProvider.isSelected() ? "No" : "Not Selected");

            if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || userName.isEmpty() || pass.isEmpty() || isClientType.equals("Not Selected")) {
                JOptionPane.showMessageDialog(frame, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    Person duplicate = null;

                    Person newAcc;
                    if(isClientType.equals("Yes")){
                        duplicate = FileFunctions.searchClient(userName);

                        if(duplicate == null)
                            newAcc = new Client(name, phone, email, userName, pass, 1000, true); //Fixed balance
                        else{
                            JOptionPane.showMessageDialog(frame, "User name already exists", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                    else{
                        duplicate = FileFunctions.searchProvider(userName);

                        if(duplicate == null)
                            newAcc = new ServiceProvider(name, phone, email, userName, pass, 1000, true); // Fixed balance
                        else{
                            JOptionPane.showMessageDialog(frame, "User name already exists", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }

                    JOptionPane.showMessageDialog(frame, "Registration Saved Successfully!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                    // Clear the form
                    nameField.setText("");
                    emailField.setText("");
                    phoneField.setText("");
                    userNameField.setText("");
                    accountTypeGroup.clearSelection();
                    passField.setText("");

                    new Login();
                    frame.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Error writing to file!", "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });

        // Login section
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 8, 0));
        loginPanel.setBounds(20, 635, 460, 30);
        loginPanel.setBackground(Color.WHITE);

        JLabel signup_suggestion = new JLabel("Already have an account?");
        signup_suggestion.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        signup_suggestion.setForeground(new Color(100, 116, 139));
        loginPanel.add(signup_suggestion);

        JButton signupBtn = new JButton("Sign In");
        signupBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        signupBtn.setForeground(new Color(59, 130, 246));
        signupBtn.setBackground(Color.WHITE);
        signupBtn.setBorder(BorderFactory.createEmptyBorder());
        signupBtn.setFocusPainted(false);
        signupBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signupBtn.setContentAreaFilled(false);
        loginPanel.add(signupBtn);

        cardPanel.add(loginPanel);

        signupBtn.addActionListener(e -> {
            new Login();
            frame.dispose();
        });

        mainPanel.add(cardPanel);
        frame.add(mainPanel);
        frame.setVisible(true);
    }
}
