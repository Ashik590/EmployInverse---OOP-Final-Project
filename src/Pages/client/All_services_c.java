package Pages.client;

import Pages.Login;
import pack.Client;
import pack.FileFunctions;
import pack.Service;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class All_services_c {
    private static ArrayList<Service> all_services;
    private static Client me_client;
    private static boolean isAll;

    public All_services_c(ArrayList<Service> all_servs, Client me){
        all_services = all_servs;
        me_client = me;

        isAll = false;

        main(null);
    }
    public All_services_c(Client me){
        all_services = FileFunctions.getAllServices();
        me_client = me;
        isAll = true;

        main(null);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("All Job Posts");
        frame.setSize(1100, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(20, 50));

        // ---------- Navigation Bar ----------
        JPanel navBar = new JPanel();
        navBar.setLayout(new BoxLayout(navBar, BoxLayout.X_AXIS));
        navBar.setBackground(new Color(50, 50, 50));
        navBar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel nameLabel = new JLabel("Client - "+ me_client.getUser_name());
        nameLabel.setForeground(Color.WHITE);
        JLabel balanceLabel = new JLabel("Balance: " + me_client.getBalance());
        balanceLabel.setForeground(Color.WHITE);

        navBar.add(nameLabel);
        navBar.add(Box.createRigidArea(new Dimension(20, 0)));
        navBar.add(balanceLabel);
        navBar.add(Box.createRigidArea(new Dimension(30, 0)));

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

        // ---------- Title ----------

        JLabel titleLabel = new JLabel("All Job Posts");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // ---------- Jobs List Panel ----------
        JPanel jobsListPanel = new JPanel();
        jobsListPanel.setLayout(new BoxLayout(jobsListPanel, BoxLayout.Y_AXIS));
        jobsListPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 10)); // left padding



        for (int i = 0; i < all_services.size(); i++) {
            int number = i + 1;
            String jobTitle = all_services.get(i).getTitle();
            String service_ID = all_services.get(i).getID();

            JPanel rowPanel = new JPanel();
            rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.X_AXIS));
            rowPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            rowPanel.setMaximumSize(new Dimension(700, 40));

            // Number label
            JLabel numberLabel = new JLabel("(" + number + ")");
            numberLabel.setPreferredSize(new Dimension(40, 25));

            // Title label
            JLabel jobLabel = new JLabel("Title: " + jobTitle);
            jobLabel.setPreferredSize(new Dimension(400, 25));

            // See more button
            JButton seeMoreBtn = new JButton("See more");
            seeMoreBtn.setMaximumSize(new Dimension(120, 30));
            seeMoreBtn.addActionListener(e -> {

                new Service_page_c(me_client, FileFunctions.searchService(service_ID));

                frame.dispose();
            });

            rowPanel.add(numberLabel);
            rowPanel.add(Box.createRigidArea(new Dimension(10, 0)));
            rowPanel.add(jobLabel);
            rowPanel.add(Box.createRigidArea(new Dimension(20, 0)));
            rowPanel.add(seeMoreBtn);

            jobsListPanel.add(rowPanel);
            jobsListPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        JScrollPane scrollPane = new JScrollPane(jobsListPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(titleLabel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        frame.add(centerPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
