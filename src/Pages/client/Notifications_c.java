package Pages.client;

import Pages.Login;
import pack.Client;
import pack.FileFunctions;
import pack.Service;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Notifications_c {
    private static Client me_client;

    public Notifications_c(Client me) {
        me_client = me;

        main(null);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Notifications");
        frame.setSize(1100, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(20, 50));

        // ---------- Navigation Bar ----------
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

        // ---------- Title ----------
        JLabel titleLabel = new JLabel("Notifications");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // ---------- Notifications List Panel ----------
        JPanel notificationListPanel = new JPanel();
        notificationListPanel.setLayout(new BoxLayout(notificationListPanel, BoxLayout.Y_AXIS));
        notificationListPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 10));


        ArrayList<Service> deliveredJobs = new ArrayList<>();
        ArrayList<Service> all_offered_jobs = me_client.getOfferedJobs();

        for (Service allOfferedJob : all_offered_jobs) {
            if (allOfferedJob.getStatus().equals("Delivered")) {
                deliveredJobs.add(allOfferedJob);
            }
        }

        for (int i = 0; i < deliveredJobs.size(); i++) {
            int number = i + 1;
            Service the_service =  deliveredJobs.get(i);
            String message = the_service.getProvider_userName() + " delivered the Job titled " + the_service.getTitle();

            JPanel rowPanel = new JPanel();
            rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.X_AXIS));
            rowPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            rowPanel.setMaximumSize(new Dimension(1000, 40));

            // Number label
            JLabel numberLabel = new JLabel(number + ".");
            numberLabel.setPreferredSize(new Dimension(30, 25));

            // Notification message
            JLabel messageLabel = new JLabel(message);
            messageLabel.setPreferredSize(new Dimension(500, 25));

            // See more button
            JButton seeMoreBtn = new JButton("See more");
            seeMoreBtn.addActionListener(e -> {
                new Service_page_c(me_client, the_service, true);
            });

            rowPanel.add(numberLabel);
            rowPanel.add(Box.createRigidArea(new Dimension(10, 0)));
            rowPanel.add(messageLabel);
            rowPanel.add(Box.createRigidArea(new Dimension(20, 0)));
            rowPanel.add(seeMoreBtn);

            notificationListPanel.add(rowPanel);
            notificationListPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        JScrollPane scrollPane = new JScrollPane(notificationListPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(titleLabel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        frame.add(centerPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
