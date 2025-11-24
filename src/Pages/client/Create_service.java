package Pages.client;

import Pages.Login;
import pack.Client;
import pack.FileFunctions;

import javax.swing.*;
import java.awt.*;

public class Create_service {
    private static Client me_client;


    public Create_service(Client me){
        me_client = me;

        main(null);
    }

    public static void main(String[] args) {
//        me_client = FileFunctions.searchClient("Ashik");
        JFrame frame = new JFrame("Create Job Page");
        frame.setSize(1100, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(20, 50));

        // --------- Navigation Bar ---------
        JPanel navBar = new JPanel();
        navBar.setLayout(new BoxLayout(navBar, BoxLayout.X_AXIS));
        navBar.setBackground(new Color(50, 50, 50));
        navBar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // User info
        JLabel title = new JLabel("Client - " + me_client.getUser_name());
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel balance = new JLabel("Balance: $" + me_client.getBalance());
        balance.setForeground(Color.WHITE);
        balance.setFont(new Font("Arial", Font.PLAIN, 14));

        navBar.add(title);
        navBar.add(Box.createRigidArea(new Dimension(20, 0)));
        navBar.add(balance);
        navBar.add(Box.createRigidArea(new Dimension(30, 0)));

        // Navigation buttons
        String[] buttons = {"All Job Posts", "Clients", "Providers", "My Account", "My Posts", "Notifications", "Logout"};
        for (String btnText : buttons) {
            JButton btn = new JButton(btnText);

            // CLICK LISTENER for nav buttons
            btn.addActionListener(e -> {
                System.out.println(btnText + " clicked");

                switch (btnText) {
                    case "All Job Posts" -> new All_services_c(FileFunctions.getAllServices(), me_client);
                    case "Clients" -> new Clients_page_c(me_client);
                    case "Providers" -> new Providers_page_c(me_client);
                    case "My Account" -> new Client_account_c(me_client);
                    case "My Posts" -> new All_services_c(me_client.getOfferedJobs(), me_client);
                    case "Notifications" -> new Notifications_c(me_client);
                    case "Logout" -> new Login();
                }

                frame.dispose();
            });

            navBar.add(btn);
            navBar.add(Box.createRigidArea(new Dimension(10, 0)));
        }

        frame.add(navBar, BorderLayout.NORTH);


        // Main portion

        // Main panel for inputs
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);  // Using absolute positioning for simplicity

        // Title label and text field
        JLabel titleLabel = new JLabel("Title:");
        titleLabel.setBounds(30, 20, 150, 25);
        JTextField titleField = new JTextField();
        titleField.setBounds(150, 20, 300, 25);

        mainPanel.add(titleLabel);
        mainPanel.add(titleField);

        // Price label and text field
        JLabel priceLabel = new JLabel("Price ($):");
        priceLabel.setBounds(30, 230, 150, 25);
        JTextField priceField = new JTextField();
        priceField.setBounds(150, 230, 100, 25);

        mainPanel.add(priceLabel);
        mainPanel.add(priceField);

        // Description label and textarea with scroll pane
        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setBounds(30, 60, 150, 25);
        JTextArea descriptionArea = new JTextArea();
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        scrollPane.setBounds(150, 60, 400, 150);

        mainPanel.add(descriptionLabel);
        mainPanel.add(scrollPane);

        // Create Button
        JButton createBtn = new JButton("Create");
        createBtn.setBounds(150, 300, 100, 30);
        mainPanel.add(createBtn);

        // Create button listener
        createBtn.addActionListener(e -> {
            String titleText = titleField.getText().trim();
            String description = descriptionArea.getText().trim();
            String priceText = priceField.getText().trim();

            if (titleText.isEmpty() || description.isEmpty() || priceText.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int price;
            try {
                price = Integer.parseInt(priceText);
                if (price < 0) {
                    JOptionPane.showMessageDialog(frame, "Price cannot be negative!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid number for price!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // TODO: Add your service creation logic here, e.g.:
             me_client.createService(titleText, description, price, true);

            JOptionPane.showMessageDialog(frame,
                "Job Created Successfully!\nTitle: " + titleText + "\nDescription: " + description + "\nPrice: $" + price,
                "Success",
                JOptionPane.INFORMATION_MESSAGE);

            // Clear input fields after creation
            titleField.setText("");
            descriptionArea.setText("");
            priceField.setText("");

            new Client_account_c(me_client);
            frame.dispose();

        });



        // Add mainPanel to frame center
        frame.add(mainPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}
