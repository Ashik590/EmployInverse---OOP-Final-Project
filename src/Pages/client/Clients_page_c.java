package Pages.client;

import Pages.Login;
import pack.Client;
import pack.FileFunctions;

import javax.swing.*;
import java.awt.*;
import java.lang.ref.Cleaner;
import java.util.ArrayList;

public class Clients_page_c {
    private static Client me_client;
    private static ArrayList<Client> clients;

    public Clients_page_c(Client me) {
        me_client = me;
        clients = FileFunctions.getAllClients();

        main(null);
    }
    public Clients_page_c(Client me, ArrayList<Client> all_clients) {
        me_client = me;
        clients = all_clients;

        main(null);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("All Clients");
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
        JLabel balanceLabel = new JLabel("Balance: "+me_client.getBalance());
        balanceLabel.setForeground(Color.WHITE);

        navBar.add(nameLabel);
        navBar.add(Box.createRigidArea(new Dimension(20, 0)));
        navBar.add(balanceLabel);
        navBar.add(Box.createRigidArea(new Dimension(30, 0)));

        String[] navButtons = {"All Job Posts", "Clients", "Providers", "My Account", "My Posts", "Notifications", "Logout"};
        for (String text : navButtons) {
            JButton btn = new JButton(text);

            btn.addActionListener(e -> {
                switch (text) {
                    case "All Job Posts" -> new All_services_c(FileFunctions.getAllServices(), me_client);
                    case "Clients" -> new Clients_page_c(me_client);
                    case "Providers" -> new Providers_page_c(me_client);
                    case "My Account" -> new Client_account_c(me_client);
                    case "My Posts" -> new All_services_c(me_client.getOfferedJobs(), me_client);
                    case "Notifications" -> new Notifications_c(me_client);
                    case "Logout" -> new Login();
                }
                frame.dispose();  // Close current frame after navigation
            });

            navBar.add(btn);
            navBar.add(Box.createRigidArea(new Dimension(10, 0)));
        }

        frame.add(navBar, BorderLayout.NORTH);

        // ---------- Title ----------
        JLabel titleLabel = new JLabel("All Clients");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // ---------- Clients List Panel ----------
        JPanel clientListPanel = new JPanel();
        clientListPanel.setLayout(new BoxLayout(clientListPanel, BoxLayout.Y_AXIS));
        clientListPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 10)); // extra left padding for alignment



        // Create numbered list aligned to the left
        for (int i = 0; i < clients.size(); i++) {
            int number = i + 1;
            Client client = clients.get(i);

            JPanel rowPanel = new JPanel();
            rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.X_AXIS));
            rowPanel.setAlignmentX(Component.LEFT_ALIGNMENT); // force left alignment
            rowPanel.setMaximumSize(new Dimension(300, 40));

            // Number label
            JLabel numberLabel = new JLabel(number + ".");
            numberLabel.setPreferredSize(new Dimension(30, 25));

            // Client button
            JButton clientButton = new JButton(client.getUser_name());
            clientButton.setMaximumSize(new Dimension(200, 35));
            clientButton.setAlignmentX(Component.LEFT_ALIGNMENT);

            clientButton.addActionListener(e -> {

                new Client_account_c(me_client, client);
                frame.dispose();
            });

            rowPanel.add(numberLabel);
            rowPanel.add(Box.createRigidArea(new Dimension(10, 0)));
            rowPanel.add(clientButton);

            clientListPanel.add(rowPanel);
            clientListPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        JScrollPane scrollPane = new JScrollPane(clientListPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(titleLabel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        frame.add(centerPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
