package Pages.client;

import Pages.Login;
import pack.Client;
import pack.FileFunctions;
import pack.ServiceProvider;

import javax.swing.*;
import java.awt.*;

public class Provider_account_c {

    private static Client me_client;
    private static ServiceProvider provider;

    public Provider_account_c(Client me, ServiceProvider sp) {
        provider = sp;
        me_client = me;

        main(null);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Account Page - Service Provider");
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

        // ---------- Info Panel ----------
        JPanel infoPanel = new JPanel(new GridLayout(8, 2, 10, 10));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Service Provider"));
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
            infoPanel.getBorder(),
            BorderFactory.createEmptyBorder(10, 10, 10, 10) // add padding inside
        ));

        // Static info rows
        infoPanel.add(new JLabel("Name:"));
        infoPanel.add(new JLabel(provider.getName()));

        infoPanel.add(new JLabel("Phone:"));
        infoPanel.add(new JLabel(provider.getPhone()));

        infoPanel.add(new JLabel("Email:"));
        infoPanel.add(new JLabel(provider.getEmail()));

        infoPanel.add(new JLabel("Username:"));
        infoPanel.add(new JLabel(provider.getUser_name()));

        infoPanel.add(new JLabel("Account Type:"));
        infoPanel.add(new JLabel("Service Provider"));

        infoPanel.add(new JLabel("Balance:"));
        infoPanel.add(new JLabel(String.valueOf(provider.getBalance())));

        infoPanel.add(new JLabel("Current Job:"));
        JButton jobBtn = new JButton(provider.getCurrentJob().getTitle());
        jobBtn.setFocusable(false);

        // CLICK LISTENER for job button
        jobBtn.addActionListener(e -> {
            System.out.println("Current Job button clicked");

            new Service_page_c(me_client, provider.getCurrentJob());
        });

        infoPanel.add(jobBtn);

        // ---------- Job Stats ----------
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
        statsPanel.setBorder(BorderFactory.createTitledBorder("Job Stats"));
        statsPanel.setBorder(BorderFactory.createCompoundBorder(
            statsPanel.getBorder(),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        statsPanel.add(new JLabel("Number of job completed: " + provider.getCompletedJobsID().size()));

        JButton seeAllBtn = new JButton("See All");
        seeAllBtn.setAlignmentX(Component.LEFT_ALIGNMENT);

        // CLICK LISTENER for see all button
        seeAllBtn.addActionListener(e -> {
            System.out.println("See All Jobs button clicked");
            // TODO: Show all completed jobs
        });

        statsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        statsPanel.add(seeAllBtn);

        // ---------- Combine Panels ----------
        JPanel centerPanel = new JPanel(new BorderLayout(10, 30));
        centerPanel.add(infoPanel, BorderLayout.NORTH);
        centerPanel.add(statsPanel, BorderLayout.CENTER);

        // ---------- Add to Frame ----------
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
