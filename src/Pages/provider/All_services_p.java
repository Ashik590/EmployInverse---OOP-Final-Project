package Pages.provider;

import Pages.Login;
import Pages.provider.Service_page_p;
import pack.Client;
import pack.FileFunctions;
import pack.Service;
import pack.ServiceProvider;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class All_services_p {
    private static ArrayList<Service> all_services;
    private static ServiceProvider me_sp;
    private static boolean isAll;

    public All_services_p(ArrayList<Service> all_servs, ServiceProvider me){
        all_services = all_servs;
        me_sp = me;

        isAll = false;

        main(null);
    }
    public All_services_p(ServiceProvider me){
        all_services = FileFunctions.getAllServices();
        me_sp = me;
        isAll = true;

        main(null);
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("All Job Posts");
        frame.setSize(1100, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(20, 50));

        // ---------- Navigation Bar ----------
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

        JLabel titleLabel = new JLabel("All Job Posts");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // ---------- Jobs List Panel ----------
        JPanel jobsListPanel = new JPanel();
        jobsListPanel.setLayout(new BoxLayout(jobsListPanel, BoxLayout.Y_AXIS));
        jobsListPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 10)); // left padding

//        String[] jobs = {
//            "Cleaning for the new apartment",
//            "Move heavy furniture",
//            "Fix broken AC",
//            "Paint the living room",
//            "Install kitchen shelves"
//        };



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

            // See more buttons
            JButton seeMoreBtn = new JButton("See more");
            seeMoreBtn.setMaximumSize(new Dimension(120, 30));
            seeMoreBtn.addActionListener(e -> {

                new Service_page_p(me_sp, FileFunctions.searchService(service_ID));

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
