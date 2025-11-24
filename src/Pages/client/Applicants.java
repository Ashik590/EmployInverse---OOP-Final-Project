package Pages.client;

import Pages.Login;
import pack.Client;
import pack.FileFunctions;
import pack.Service;
import pack.ServiceProvider;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Applicants {
    private static Client me_client;
    private static Service service;

    public Applicants(Client me, Service service_) {
        service = service_;
        me_client = me;

        main(null);
    }

    public static void main(String[] args) {
//        service = FileFunctions.searchService("7606d148-26d9-4a55-a16f-e84b93fea447");
//        me_client = FileFunctions.searchClient("Ashik");

        JFrame frame = new JFrame("Applicants");
        frame.setSize(1100, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(20, 50));

        // ---------- Navigation Bar ----------
        JPanel navBar = new JPanel();
        navBar.setLayout(new BoxLayout(navBar, BoxLayout.X_AXIS));
        navBar.setBackground(new Color(50, 50, 50));
        navBar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel nameLabel = new JLabel("Client - "+me_client.getUser_name());
        nameLabel.setForeground(Color.WHITE);
        JLabel balanceLabel = new JLabel("Balance: "+me_client.getBalance() );
        balanceLabel.setForeground(Color.WHITE);

        navBar.add(nameLabel);
        navBar.add(Box.createRigidArea(new Dimension(20, 0)));
        navBar.add(balanceLabel);
        navBar.add(Box.createRigidArea(new Dimension(30, 0)));

        String[] navButtons = {"All Job Posts", "Clients", "Providers", "My Account", "My Posts", "Notifications", "Logout"};
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
            } );
            navBar.add(btn);
            navBar.add(Box.createRigidArea(new Dimension(10, 0)));
        }

        frame.add(navBar, BorderLayout.NORTH);

        // ---------- Title ----------
        JLabel titleLabel = new JLabel("Applicants");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // ---------- Applicants List Panel ----------
        JPanel applicantsListPanel = new JPanel();
        applicantsListPanel.setLayout(new BoxLayout(applicantsListPanel, BoxLayout.Y_AXIS));
        applicantsListPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 10)); // extra left padding for alignment

        // Service Button
        JButton the_service = new JButton("See The Service");
        the_service.setBounds(160, 112, 180, 30);
        frame.add(the_service);

        the_service.addActionListener(e -> {
            new Service_page_c(me_client, service);
            frame.dispose();
        });


        ArrayList<ServiceProvider> applicants = service.getApplicants();

        // Create numbered list aligned to the left
        for (int i = 0; i < applicants.size(); i++) {
            int number = i + 1;
            ServiceProvider applicant = applicants.get(i);

            JPanel rowPanel = new JPanel();
            rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.X_AXIS));
            rowPanel.setAlignmentX(Component.LEFT_ALIGNMENT); // force left alignment
            rowPanel.setMaximumSize(new Dimension(300, 40));

            // Number label
            JLabel numberLabel = new JLabel(number + ".");
            numberLabel.setPreferredSize(new Dimension(30, 25));

            // Applicant button
            JButton applicantButton = new JButton(applicant.getName());
            applicantButton.setMaximumSize(new Dimension(200, 35));
            applicantButton.setAlignmentX(Component.LEFT_ALIGNMENT);

            applicantButton.addActionListener(e -> {

                new Applicant_pop(me_client, applicant, service);
            });

            rowPanel.add(numberLabel);
            rowPanel.add(Box.createRigidArea(new Dimension(10, 0)));
            rowPanel.add(applicantButton);

            applicantsListPanel.add(rowPanel);
            applicantsListPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        JScrollPane scrollPane = new JScrollPane(applicantsListPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(titleLabel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        frame.add(centerPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
