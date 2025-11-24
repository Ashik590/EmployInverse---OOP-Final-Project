package Pages.provider;

import Pages.Login;
import pack.Client;
import pack.FileFunctions;
import pack.Service;
import pack.ServiceProvider;

import javax.swing.*;
import java.awt.*;


public class Service_page_p {
    private static ServiceProvider me_sp;
    private static Service service;
    private static boolean mine;

    public Service_page_p(ServiceProvider me) {
        me_sp = me;
        service = me.getCurrentJob();
        mine = true;

        main(null);
    }

    public Service_page_p(ServiceProvider me,  Service service_) {
        me_sp = me;
        service = service_;

        mine = service_.getProvider_userName().equals(me.getUser_name());

        main(null);

    }

    public static void main(String[] args) {

        System.out.println(service.getStatus());
        System.out.println(service.getProvider_userName());

        JFrame frame = new JFrame("Service Details");
        frame.setSize(1100, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

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

        // --------- Main Panel ---------
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 100));

        mainPanel.add(sectionLabel("Service Details"));

        mainPanel.add(line("Title", service.getTitle()));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 8))); // SPACE between lines

        mainPanel.add(line("Description", service.getDescription()));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 8)));

        mainPanel.add(buttonLine("Client", service.getClient_userName(), e -> {
            System.out.println("Client button clicked");
            new Client_account_p(me_sp, service.getClient());

            frame.dispose();
        }));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 8)));

        mainPanel.add(line("Status", service.getStatus()));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 8)));

        String providerNameText = me_sp.getUser_name().equals(service.getProvider_userName()) ? "Me" : service.getProvider_userName();
        mainPanel.add(buttonLine("Provider", providerNameText, e -> {
            System.out.println("Provider button clicked");
            // TODO: Open provider details page
            if(service.getProvider_userName().equals("None"))
                JOptionPane.showMessageDialog(frame, "Provider is not available", "Error", JOptionPane.ERROR_MESSAGE);
            else{
                new Provider_account_p(me_sp, service.getProvider());
                frame.dispose();
            }

        }));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 8)));


        mainPanel.add(line("Price", String.valueOf(service.getPrice())));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15))); // Bigger space before action buttons

        // Action buttons panel (Apply, Cancellation Request, Deliver, Cancel)
        JPanel actionButtonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));

        boolean isAppliedByMe = service.getApplicants_userName().contains(me_sp.getUser_name());

        String applyText = isAppliedByMe ? "Applied" : "Apply";

        if(service.getStatus().equals("Incompleted")){
            JButton applyButton = new JButton(applyText);
            applyButton.addActionListener(e ->{
                if(isAppliedByMe)
                    JOptionPane.showMessageDialog(frame, "You had applied to this job!", "Error", JOptionPane.ERROR_MESSAGE);
                else{
                    JOptionPane.showMessageDialog(frame, "Application Done", "Success", JOptionPane.INFORMATION_MESSAGE);
                    service.addApplicant(me_sp.getUser_name());
                }

                new Service_page_p(me_sp, service);
                frame.dispose();

            });

            actionButtonsPanel.add(applyButton);
        }


        if(mine && service.getStatus().equals("On-going")){
            JButton deliverButton = new JButton("Deliver");
            deliverButton.addActionListener(e -> {
                System.out.println("Deliver button clicked");

                me_sp.deliverForJob();

                new Service_page_p(me_sp);
                frame.dispose();
            });

            JButton cancelButton = new JButton("Cancel");
            cancelButton.addActionListener(e -> {
                System.out.println("Cancel button clicked");

                me_sp.cancelJob();

                new Provider_account_p(me_sp);
                frame.dispose();

            });

            actionButtonsPanel.add(deliverButton);
            actionButtonsPanel.add(cancelButton);

        }



        if(mine && service.getStatus().equals("On-request-cancellation")){
            JButton cancelCancellationRequest = new JButton("Cancel Cancellation Request");
            cancelCancellationRequest.addActionListener(e -> {
                System.out.println("Cancel button clicked");

                me_sp.denyCancellationRequest();

                JOptionPane.showMessageDialog(frame, "You are continuing this job!", "Cancellation Request Cancelled", JOptionPane.INFORMATION_MESSAGE);

                new Service_page_p(me_sp, service);
                frame.dispose();
            });

            JButton acceptCancellationRequest = new JButton("Accept Cancellation Request");
            acceptCancellationRequest.addActionListener(e -> {
                System.out.println("Cancel button clicked");

                me_sp.cancelJob();
                JOptionPane.showMessageDialog(frame, "You agreed to cancel this job!", "Cancellation Request Accepted", JOptionPane.INFORMATION_MESSAGE);

                new Service_page_p(me_sp, service);
                frame.dispose();
            });

            actionButtonsPanel.add(cancelCancellationRequest);
            actionButtonsPanel.add(acceptCancellationRequest);
        }


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
