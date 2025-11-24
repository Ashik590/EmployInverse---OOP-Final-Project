package Pages.client;

import Pages.Login;
import pack.Client;
import pack.FileFunctions;

import javax.swing.*;
import java.awt.*;

public class Client_account_c {

    private static Client me_client;
    private static Client client;
    private static boolean isMe;

    public Client_account_c(Client me) {
        me_client = me;
        client = me;
        isMe = true;

        main(null);
    }
    public Client_account_c(Client me, Client client_) {
        me_client = me;
        client = client_;
        isMe = me.getUser_name().equals(client_.getUser_name());

        main(null);
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("Account Page - Client");
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
        String[] navButtons = {"All Job Posts", "Clients", "Providers", "My Account", "My Posts", "Notifications","Logout"};
        for (String text : navButtons) {
            JButton btn = new JButton(text);

            btn.addActionListener(e ->{
                switch (text) {
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

        // ---------- Static Account Info Panel ----------

        if(isMe){
            JButton signupBtn = new JButton("Create Job");
            signupBtn.setBounds(15, 550, 350, 40);
            frame.add(signupBtn);

            signupBtn.addActionListener(e -> {
                new Create_service(me_client);
                frame.dispose();
            });
        }

        JPanel infoPanel = new JPanel();
        String borderTitle;

        if(isMe)
            borderTitle = "My Account";
        else
            borderTitle = "Client Account";

        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(borderTitle),
            BorderFactory.createEmptyBorder(10, 15, 10, 10)
        ));

        infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        infoPanel.add(createRow("Name:", client.getName()));
        infoPanel.add(createRow("Phone:", client.getPhone()));
        infoPanel.add(createRow("Email:", client.getEmail()));
        infoPanel.add(createRow("Username:", client.getUser_name()));
        infoPanel.add(createRow("Account Type:", "Client"));

        if(isMe)
            infoPanel.add(createRow("Balance:",  String.valueOf(client.getBalance())));

        // ---------- Job Stats Panel ----------
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
        statsPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Job Stats"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        statsPanel.add(new JLabel("Number of job posted: "+ client.getOfferedJobsID().size()));

        JButton seeAllBtn = new JButton("See All");
        seeAllBtn.setAlignmentX(Component.LEFT_ALIGNMENT);

        // CLICK LISTENER for "See All" button
        seeAllBtn.addActionListener(e -> {
            System.out.println("See All Jobs button clicked");
            // TODO: Show all posted jobs for this client

            new All_services_c(client.getOfferedJobs(), me_client);
            frame.dispose();
        });

        statsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        statsPanel.add(seeAllBtn);

        // ---------- Combine Info and Stats ----------
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout(10, 30));
        centerPanel.add(infoPanel, BorderLayout.NORTH);
        centerPanel.add(statsPanel, BorderLayout.CENTER);

        frame.add(centerPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    // Helper method to create a label row with spacing
    private static JPanel createRow(String label, String value) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        JLabel labelComp = new JLabel(label);
        labelComp.setPreferredSize(new Dimension(120, 25));
        JLabel valueComp = new JLabel(value);
        row.add(labelComp);
        row.add(valueComp);
        return row;
    }
}
