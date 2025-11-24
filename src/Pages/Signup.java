package Pages;

import pack.Client;
import pack.FileFunctions;
import pack.Person;
import pack.ServiceProvider;

import javax.swing.*;
import java.io.*;


public class Signup {

    public Signup() {
        main(null);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Signup");
        frame.setSize(800, 700);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);

        // Name
        JLabel nameLabel = new JLabel("*Name :");
        nameLabel.setBounds(30, 20, 150, 25);
        JTextField nameField = new JTextField();
        nameField.setBounds(150, 20, 300, 25);
        frame.add(nameLabel);
        frame.add(nameField);

        // Phone
        JLabel phoneLabel = new JLabel("*Phone :");
        phoneLabel.setBounds(30, 60, 100, 25);
        JTextField phoneField = new JTextField();
        phoneField.setBounds(150, 60, 300, 25);
        frame.add(phoneLabel);
        frame.add(phoneField);

        // Email
        JLabel emailLabel = new JLabel("*Email :");
        emailLabel.setBounds(30, 100, 100, 25);
        JTextField emailField = new JTextField();
        emailField.setBounds(150, 100, 300, 25);
        frame.add(emailLabel);
        frame.add(emailField);

        // User name
        JLabel userNameLabel = new JLabel("*User name :");
        userNameLabel.setBounds(30, 140, 100, 25);
        JTextField userNameField = new JTextField();
        userNameField.setBounds(150, 140, 300, 25);
        frame.add(userNameLabel);
        frame.add(userNameField);


        // Password
        JLabel passwordLabel = new JLabel("*Password:");
        passwordLabel.setBounds(30, 180, 100, 25);
        JTextField passField = new JPasswordField();
        passField.setBounds(150, 180, 300, 25);
        frame.add(passwordLabel);
        frame.add(passField);

        // Account type
        JLabel accountTypeLabel = new JLabel("*Account Type : ");
        accountTypeLabel.setBounds(30, 260, 340, 25);
        JRadioButton isClient = new JRadioButton("Client");
        isClient.setBounds(200, 260, 100, 25);
        JRadioButton isProvider = new JRadioButton("Service Provider");
        isProvider.setBounds(300, 260, 150, 25);
        ButtonGroup accountTypeGroup = new ButtonGroup();
        accountTypeGroup.add(isClient);
        accountTypeGroup.add(isProvider);
        frame.add(accountTypeLabel);
        frame.add(isClient);
        frame.add(isProvider);

        // Login section
        JLabel signup_suggestion = new JLabel("Wants to login ?");
        signup_suggestion.setBounds(30, 500, 120, 25);
        frame.add(signup_suggestion);

        JButton signupBtn = new JButton("login");
        signupBtn.setBounds(200, 500, 120, 30);
        frame.add(signupBtn);

        signupBtn.addActionListener(e -> {
            new Login();
            frame.dispose();
        });

        // Register Button
        JButton registerBtn = new JButton("Register");
        registerBtn.setBounds(30, 350, 120, 30);
        frame.add(registerBtn);

        registerBtn.addActionListener(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
            String userName = userNameField.getText();
            String pass = passField.getText().isEmpty() ? "Not mentioned" : passField.getText();
            String isClientType = isClient.isSelected() ? "Yes" : (isProvider.isSelected() ? "No" : "Not Selected");

            if (name.isEmpty() || email.isEmpty() || phone.isEmpty() ||userName.isEmpty() || pass.isEmpty() ||  isClientType.equals("Not Selected")) {
                JOptionPane.showMessageDialog(frame, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                try {

                    Person duplicate = null;

                    Person newAcc;
                    if(isClientType.equals("Yes")){
                        duplicate = FileFunctions.searchClient(userName);

                        if(duplicate == null)
                            newAcc = new Client(name, phone,email, userName, pass,1000 ,true); //Fixed balance
                        else{
                            JOptionPane.showMessageDialog(frame, "User name already exists", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                    else{
                        duplicate = FileFunctions.searchProvider(userName);

                        if(duplicate == null)
                            newAcc = new ServiceProvider(name, phone, email, userName, pass, 1000,true); // Fixed balance
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

        frame.setVisible(true);
    }
}
