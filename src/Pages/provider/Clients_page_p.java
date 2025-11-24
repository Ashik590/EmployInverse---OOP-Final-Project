package Pages.provider;

import Pages.Login;
import Pages.provider.Service_page_p;
import pack.Client;
import pack.FileFunctions;
import pack.ServiceProvider;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Clients_page_p {
    private static ServiceProvider me_sp;
    private static ArrayList<Client> clients;

    public Clients_page_p(ServiceProvider me) {
        me_sp = me;
        clients = FileFunctions.getAllClients();

        main(null);
    }
    public Clients_page_p(ServiceProvider me, ArrayList<Client> all_clients) {
        me_sp = me;
        clients = all_clients;

        main(null);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("All Clients");
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

                new Client_account_p(me_sp, client);
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
