package UI;
import UI.client.Client_account_c;
import UI.provider.Provider_account_p;
import logic.Client;
import logic.FileFunctions;
import logic.Person;
import logic.ServiceProvider;

import javax.swing.*;
import java.awt.*;

public class Login {

    public Login(){
        main(null);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("EmployInverse - Login");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(900, 650));

        // Main panel with modern background
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(new Color(245, 247, 250));

        // Login card panel
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(null);
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setPreferredSize(new Dimension(500, 550));
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

        JLabel subtitleLabel = new JLabel("Sign in to continue");
        subtitleLabel.setBounds(10, 65, 480, 20);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(100, 116, 139));
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        cardPanel.add(subtitleLabel);

        // User name
        JLabel userNameLabel = new JLabel("Username");
        userNameLabel.setBounds(20, 120, 380, 20);
        userNameLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        userNameLabel.setForeground(new Color(51, 65, 85));
        cardPanel.add(userNameLabel);

        JTextField userNameField = new JTextField();
        userNameField.setBounds(20, 145, 460, 42);
        userNameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        userNameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(203, 213, 225), 1),
            BorderFactory.createEmptyBorder(5, 12, 5, 12)
        ));
        cardPanel.add(userNameField);

        // Password
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(20, 205, 380, 20);
        passwordLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        passwordLabel.setForeground(new Color(51, 65, 85));
        cardPanel.add(passwordLabel);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(20, 230, 460, 42);
        passField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(203, 213, 225), 1),
            BorderFactory.createEmptyBorder(5, 12, 5, 12)
        ));
        cardPanel.add(passField);

        // Account type
        JLabel accountTypeLabel = new JLabel("Account Type");
        accountTypeLabel.setBounds(20, 290, 380, 20);
        accountTypeLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        accountTypeLabel.setForeground(new Color(51, 65, 85));
        cardPanel.add(accountTypeLabel);

        JRadioButton isClient = new JRadioButton("Client");
        isClient.setBounds(20, 315, 180, 30);
        isClient.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        isClient.setBackground(Color.WHITE);
        isClient.setFocusPainted(false);

        JRadioButton isProvider = new JRadioButton("Service Provider");
        isProvider.setBounds(200, 315, 190, 30);
        isProvider.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        isProvider.setBackground(Color.WHITE);
        isProvider.setFocusPainted(false);

        ButtonGroup accountTypeGroup = new ButtonGroup();
        accountTypeGroup.add(isClient);
        accountTypeGroup.add(isProvider);
        cardPanel.add(isClient);
        cardPanel.add(isProvider);

        // Register Button
        JButton registerBtn = new JButton("Sign In");
        registerBtn.setBounds(20, 375, 460, 45);
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
            String userName = userNameField.getText();
            String pass = new String(passField.getPassword()).isEmpty() ? "Not mentioned" : new String(passField.getPassword());
            String isClientType = isClient.isSelected() ? "Yes" : (isProvider.isSelected() ? "No" : "Not Selected");

            if (userName.isEmpty() || pass.isEmpty() || isClientType.equals("Not Selected")) {
                JOptionPane.showMessageDialog(frame, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    Person person = null;
                    if(isClientType.equals("Yes")) {
                        person = FileFunctions.searchClient(userName);
                    }else{
                        person = FileFunctions.searchProvider(userName);
                    }

                    if(person == null) {
                        JOptionPane.showMessageDialog(frame, "Account is not found!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if(!person.checkPass(pass)){
                        JOptionPane.showMessageDialog(frame, "Wrong password!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    JOptionPane.showMessageDialog(frame, "Logged in Successfully!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                    System.out.println(person);

                    if(isClientType.equals("Yes"))
                        new Client_account_c((Client) person);
                    else
                        new Provider_account_p((ServiceProvider) person);

                    // Clear the form
                    userNameField.setText("");
                    passField.setText("");
                    accountTypeGroup.clearSelection();

                    frame.dispose();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Error writing to file!", "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });

        // Signup section
        JPanel signupPanel = new JPanel();
        signupPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 8, 0));
        signupPanel.setBounds(20, 445, 460, 30);
        signupPanel.setBackground(Color.WHITE);

        JLabel signup_suggestion = new JLabel("Don't have an account?");
        signup_suggestion.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        signup_suggestion.setForeground(new Color(100, 116, 139));
        signupPanel.add(signup_suggestion);

        JButton signupBtn = new JButton("Sign Up");
        signupBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        signupBtn.setForeground(new Color(59, 130, 246));
        signupBtn.setBackground(Color.WHITE);
        signupBtn.setBorder(BorderFactory.createEmptyBorder());
        signupBtn.setFocusPainted(false);
        signupBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signupBtn.setContentAreaFilled(false);
        signupPanel.add(signupBtn);

        cardPanel.add(signupPanel);

        signupBtn.addActionListener(e -> {
            new Signup();
            frame.dispose();
        });

        mainPanel.add(cardPanel);
        frame.add(mainPanel);
        frame.setVisible(true);
    }
}
