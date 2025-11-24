package Pages.provider;

import Pages.Login;
import Pages.provider.Service_page_p;
import pack.Client;
import pack.FileFunctions;
import pack.ServiceProvider;

import javax.swing.*;
import java.awt.*;

public class Client_account_p {

    private static ServiceProvider me_sp;
    private static Client client;


    public Client_account_p(ServiceProvider me, Client client_) {
        me_sp = me;
        client = client_;

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
        JLabel title = new JLabel("Provider - " + me_sp.getUser_name());
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel balance = new JLabel("Balance: $" + me_sp.getBalance());
        balance.setForeground(Color.WHITE);
        balance.setFont(new Font("Arial", Font.PLAIN, 14));

        navBar.add(title);
        navBar.add(Box.createRigidArea(new Dimension(20, 0)));
        navBar.add(balance);
        navBar.add(Box.createRigidArea(new Dimension(30, 0)));

        // Navigation buttons
        String[] buttons = {"All Job Posts", "Clients", "Providers", "My Account", "My Job", "Notifications", "Logout"};
        for (String btnText : buttons) {
            JButton btn = new JButton(btnText);

            // CLICK LISTENER for nav buttons
            btn.addActionListener(e -> {
                System.out.println(btnText + " clicked");

                switch (btnText) {
                    case "All Job Posts" -> {
                        new All_services_p(FileFunctions.getAllServices(), me_sp);
                        frame.dispose();
                    }
                    case "Clients" -> {
                        new Clients_page_p(me_sp);
                        frame.dispose();
                    }
                    case "Providers" -> {
                        new Providers_page_p(me_sp);
                        frame.dispose();
                    }
                    case "My Account" -> {
                        new Provider_account_p(me_sp);
                        frame.dispose();
                    }
                    case "My Job" -> {
                        if(me_sp.getCurrentJobID().equals("None"))
                            JOptionPane.showMessageDialog(frame, "I have no job!", "Error", JOptionPane.ERROR_MESSAGE);
                        else{
                            new Service_page_p(me_sp);
                            frame.dispose();
                        }
                    }
                    case "Notifications" -> {
                        new Notifications_p(me_sp);
                        frame.dispose();
                    }
                    case "Logout" -> {
                        new Login();
                        frame.dispose();
                    }
                }

            });

            navBar.add(btn);
            navBar.add(Box.createRigidArea(new Dimension(10, 0)));
        }

        frame.add(navBar, BorderLayout.NORTH);

        // ---------- Static Account Info Panel ----------

        JPanel infoPanel = new JPanel();

        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Client Account"),
            BorderFactory.createEmptyBorder(10, 15, 10, 10)
        ));

        infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        infoPanel.add(createRow("Name:", client.getName()));
        infoPanel.add(createRow("Phone:", client.getPhone()));
        infoPanel.add(createRow("Email:", client.getEmail()));
        infoPanel.add(createRow("Username:", client.getUser_name()));
        infoPanel.add(createRow("Account Type:", "Client"));

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

            new All_services_p(client.getOfferedJobs(), me_sp);
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
