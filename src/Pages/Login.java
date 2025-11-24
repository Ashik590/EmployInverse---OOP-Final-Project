package Pages;
import Pages.client.Client_account_c;
import Pages.provider.Provider_account_p;
import pack.Client;
import pack.FileFunctions;
import pack.Person;
import pack.ServiceProvider;

import javax.swing.*;

public class Login {

    public Login(){
        main(null);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Login");
        frame.setSize(800, 600);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);


        // User name
        JLabel userNameLabel = new JLabel("*User name :");
        userNameLabel.setBounds(30, 50, 100, 25);
        JTextField userNameField = new JTextField();
        userNameField.setBounds(150, 50, 300, 25);
        frame.add(userNameLabel);
        frame.add(userNameField);


        // Password
        JLabel passwordLabel = new JLabel("*Password:");
        passwordLabel.setBounds(30, 90, 100, 25);
        JTextField passField = new JPasswordField();
        passField.setBounds(150, 90, 300, 25);
        frame.add(passwordLabel);
        frame.add(passField);

        // Account type
        JLabel accountTypeLabel = new JLabel("*Account Type : ?");
        accountTypeLabel.setBounds(30, 170, 340, 25);
        JRadioButton isClient = new JRadioButton("Client");
        isClient.setBounds(200, 170, 100, 25);
        JRadioButton isProvider = new JRadioButton("Service Provider");
        isProvider.setBounds(300, 170, 150, 25);
        ButtonGroup accountTypeGroup = new ButtonGroup();
        accountTypeGroup.add(isClient);
        accountTypeGroup.add(isProvider);
        frame.add(accountTypeLabel);
        frame.add(isClient);
        frame.add(isProvider);

        // Signup section
        JLabel signup_suggestion = new JLabel("Wants to signup ?");
        signup_suggestion.setBounds(30, 400, 120, 25);
        frame.add(signup_suggestion);

        JButton signupBtn = new JButton("Signup");
        signupBtn.setBounds(200, 400, 120, 30);
        frame.add(signupBtn);

        signupBtn.addActionListener(e -> {
            new Signup();
            frame.dispose();
        });

        // Register Button
        JButton registerBtn = new JButton("Register");
        registerBtn.setBounds(30, 250, 120, 30);
        frame.add(registerBtn);

        registerBtn.addActionListener(e -> {
            String userName = userNameField.getText();
            String pass = passField.getText().isEmpty() ? "Not mentioned" : passField.getText();
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

        frame.setVisible(true);
    }
}
