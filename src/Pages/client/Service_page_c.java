package Pages.client;

import Pages.Login;
import pack.Client;
import pack.FileFunctions;
import pack.Service;

import javax.swing.*;
import java.awt.*;


public class Service_page_c {
    private static Client me_client;
    private static Service service;
    private static boolean mine;

    public Service_page_c(Client me,  Service service_,  boolean isMine) {
        me_client = me;
        service = service_;
        mine = isMine;

        main(null);
    }

    public Service_page_c(Client me,  Service service_) {
        me_client = me;
        service = service_;

        mine = me_client.getOfferedJobsID().contains(service.getID());

        main(null);

    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("Service Details");
        frame.setSize(1100, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // --------- Navigation Bar ---------
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

        // --------- Main Panel ---------
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 100));

        mainPanel.add(sectionLabel("Service Details"));

        mainPanel.add(line("Title", service.getTitle()));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 8))); // SPACE between lines

        mainPanel.add(line("Description", service.getDescription()));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 8)));

        String clientText = mine ? "Me" : service.getClient_userName();
        mainPanel.add(buttonLine("Client", clientText, e -> {
            System.out.println("Client button clicked");
            new Client_account_c(me_client, service.getClient());

            frame.dispose();
        }));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 8)));

        mainPanel.add(line("Status", service.getStatus()));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 8)));

        mainPanel.add(buttonLine("Provider", service.getProvider_userName(), e -> {
            System.out.println("Provider button clicked");
            // TODO: Open provider details page
            if(service.getProvider_userName().equals("None"))
                JOptionPane.showMessageDialog(frame, "Provider is not available", "Error", JOptionPane.ERROR_MESSAGE);
            else{
                new Provider_account_c(me_client, service.getProvider());
                frame.dispose();
            }

        }));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 8)));


        mainPanel.add(line("Price", String.valueOf(service.getPrice())));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15))); // Bigger space before action buttons

        // Action buttons panel (Apply, Cancellation Request, Deliver, Cancel)
        JPanel actionButtonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));

//        JButton applyButton = new JButton("Apply");
//        applyButton.addActionListener(e -> System.out.println("Apply button clicked"));

        if(mine && service.getStatus().equals("On-going")) {
            JButton cancelRequestButton = new JButton("Make Cancellation Request");
            cancelRequestButton.addActionListener(e ->{
                me_client.cancellationRequest(service);
                JOptionPane.showMessageDialog(frame, "Cancellation Request Done. Wait for the Provider!", "Success",
                    JOptionPane.INFORMATION_MESSAGE);

                frame.dispose();
                new Service_page_c(me_client, service);
            });
            actionButtonsPanel.add(cancelRequestButton);

        }

        if(mine && service.getStatus().equals("Delivered")) {
            JButton acceptDelivery = new JButton("Accept Delivery");
            acceptDelivery.addActionListener(e -> {
                me_client.acceptDelivery(service);
                JOptionPane.showMessageDialog(frame, "Great for you!", "Success",
                    JOptionPane.INFORMATION_MESSAGE);

                new Service_page_c(me_client, service);
                frame.dispose();
            });
            actionButtonsPanel.add(acceptDelivery);
        }

//        JButton deliverButton = new JButton("Deliver");
//        deliverButton.addActionListener(e -> System.out.println("Deliver button clicked"));

//        JButton cancelButton = new JButton("Cancel");
//        cancelButton.addActionListener(e -> System.out.println("Cancel button clicked"));

//        JButton cancelCancellationRequest = new JButton("Cancel Cancellation Request");
//        cancelCancellationRequest.addActionListener(e -> System.out.println("Cancel button clicked"));

//        JButton acceptCancellationRequest = new JButton("Accept Cancellation Request");
//        acceptCancellationRequest.addActionListener(e -> System.out.println("Cancel button clicked"));

        if (mine && !service.getStatus().equals("Completed")) {
            JButton applicantBtn = new JButton("Applicants");
            applicantBtn.addActionListener(e -> {
                new Applicants(me_client, service);
                frame.dispose();
            });
            actionButtonsPanel.add(applicantBtn);

        }

        if(mine && service.getStatus().equals("Incompleted")) {
            JButton deleteServiceBtn = new JButton("Delete");
            deleteServiceBtn.addActionListener(e -> {
                me_client.deleteJob(service);
                JOptionPane.showMessageDialog(frame, "The Service is deleted!", "Success", JOptionPane.INFORMATION_MESSAGE);
                new Client_account_c(me_client);
                frame.dispose();
            });
            actionButtonsPanel.add(deleteServiceBtn);
        }

//        actionButtonsPanel.add(applyButton);
//        actionButtonsPanel.add(deliverButton);
//        actionButtonsPanel.add(cancelButton);
//        actionButtonsPanel.add(cancelCancellationRequest);
//        actionButtonsPanel.add(acceptCancellationRequest);

        mainPanel.add(actionButtonsPanel);

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    // Creates a row with label and value
    private static JPanel line(String labelText, String valueText) {
        JPanel linePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        JLabel label = new JLabel(labelText + ": ");
        label.setPreferredSize(new Dimension(150, 25));
        JLabel value = new JLabel(valueText);
        linePanel.add(label);
        linePanel.add(value);
        linePanel.setMaximumSize(new Dimension(900, 30));
        return linePanel;
    }

    // Creates a row with a label and a clickable button
    private static JPanel buttonLine(String labelText, String buttonText, java.awt.event.ActionListener listener) {
        JPanel linePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        JLabel label = new JLabel(labelText + ": ");
        label.setPreferredSize(new Dimension(150, 25));

        JButton button = new JButton(buttonText);
        button.addActionListener(listener);

        linePanel.add(label);
        linePanel.add(button);
        linePanel.setMaximumSize(new Dimension(900, 30));
        return linePanel;
    }

    // Creates a row with a label, value, and a "See All" button
    private static JPanel applicantLine(String labelText, String valueText, java.awt.event.ActionListener seeAllListener) {
        JPanel linePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        JLabel label = new JLabel(labelText + ": ");
        label.setPreferredSize(new Dimension(150, 25));

        JLabel value = new JLabel(valueText);

        JButton seeAllBtn = new JButton("See All");
        seeAllBtn.setMargin(new Insets(2, 8, 2, 8));
        seeAllBtn.addActionListener(seeAllListener);

        linePanel.add(label);
        linePanel.add(value);
        linePanel.add(Box.createRigidArea(new Dimension(10, 0))); // small space between number and button
        linePanel.add(seeAllBtn);

        linePanel.setMaximumSize(new Dimension(900, 30));
        return linePanel;
    }

    // Creates section header labels
    private static JLabel sectionLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        return label;
    }
}
