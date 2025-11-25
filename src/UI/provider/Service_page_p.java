package UI.provider;

import UI.Login;
import logic.FileFunctions;
import logic.Service;
import logic.ServiceProvider;

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

    public Service_page_p(ServiceProvider me, Service service_) {
        me_sp = me;
        service = service_;

        mine = service_.getProvider_userName().equals(me.getUser_name());

        main(null);

    }

    public static void main(String[] args) {

        System.out.println(service.getStatus());
        System.out.println(service.getProvider_userName());

        JFrame frame = new JFrame("EmployInverse - Job Details");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(1200, 800));

        // Main container
        JPanel mainContainer = new JPanel(new BorderLayout());
        mainContainer.setBackground(new Color(245, 247, 250));

        // ---------- Navigation Bar ----------
        JPanel navBar = new JPanel();
        navBar.setLayout(new BorderLayout());
        navBar.setBackground(Color.WHITE);
        navBar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 225, 230)),
            BorderFactory.createEmptyBorder(15, 30, 15, 30)
        ));

        // Left side - Brand and user info
        JPanel leftNav = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        leftNav.setBackground(Color.WHITE);

        JLabel brandLabel = new JLabel("EmployInverse");
        brandLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        brandLabel.setForeground(new Color(59, 130, 246));

        JLabel nameLabel = new JLabel("Provider: " + me_sp.getUser_name());
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        nameLabel.setForeground(new Color(71, 85, 105));

        JLabel balanceLabel = new JLabel("Balance: $" + me_sp.getBalance());
        balanceLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        balanceLabel.setForeground(new Color(16, 185, 129));

        // Forward and Backward Buttons start
        JButton backBtn = new JButton("<");
        backBtn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        backBtn.setForeground(new Color(255, 255, 255));

        JButton forwardBtn = new JButton(">");
        forwardBtn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        forwardBtn.setForeground(new Color(255, 255, 255));

        backBtn.setBackground(new Color(61, 171, 255));
        backBtn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        backBtn.setFocusPainted(false);
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        forwardBtn.setBackground(new Color(43, 118, 242));
        forwardBtn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        forwardBtn.setFocusPainted(false);
        forwardBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Forward and Backward Buttons end

        leftNav.add(brandLabel);
        leftNav.add(createSeparator());
        leftNav.add(nameLabel);
        leftNav.add(balanceLabel);
        leftNav.add(createSeparator());
        leftNav.add(backBtn);
        leftNav.add(forwardBtn);

        // Right side - Navigation buttons
        JPanel rightNav = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightNav.setBackground(Color.WHITE);

        String[] navButtons = {"All Job Posts", "Clients", "Providers", "My Account", "My Job", "Notifications", "Logout"};
        for (String text : navButtons) {
            JButton btn = createNavButton(text);

            btn.addActionListener(e -> {
                switch (text) {
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

            rightNav.add(btn);
        }

        navBar.add(leftNav, BorderLayout.WEST);
        navBar.add(rightNav, BorderLayout.EAST);

        mainContainer.add(navBar, BorderLayout.NORTH);

        // ---------- Content Area ----------
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(245, 247, 250));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100)); // Larger side margins for details view

        // Title Section
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(245, 247, 250));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 25, 0));
        titlePanel.setMaximumSize(new Dimension(1000, 80));
        titlePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel("Service Details");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(30, 41, 59));

        JLabel subtitleLabel = new JLabel(service.getTitle());
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(100, 116, 139));

        titlePanel.add(titleLabel, BorderLayout.NORTH);
        titlePanel.add(subtitleLabel, BorderLayout.CENTER);

        // Details Card
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 225, 230), 1),
            BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));
        card.setMaximumSize(new Dimension(1000, Integer.MAX_VALUE)); // Allow height flexibility
        card.setAlignmentX(Component.CENTER_ALIGNMENT);


        // Data Rows
        card.add(createInfoRow("Service Title", service.getTitle()));
        card.add(createDivider());

        // Description
        card.add(createInfoRow("Description", service.getDescription()));
        card.add(createDivider());

        // Client Link
        card.add(createLinkRow("Client", service.getClient_userName(), e -> {
            new Client_account_p(me_sp, service.getClient());
            frame.dispose();
        }));
        card.add(createDivider());

        // Status
        card.add(createInfoRow("Status", service.getStatus()));
        card.add(createDivider());

        // Provider Link
        String providerNameText = me_sp.getUser_name().equals(service.getProvider_userName()) ? "Me" : service.getProvider_userName();
        card.add(createLinkRow("Provider", providerNameText, e -> {

            if(service.getProvider_userName().equals("None"))
                JOptionPane.showMessageDialog(frame, "Provider is not available", "Error", JOptionPane.ERROR_MESSAGE);
            else{
                new Provider_account_p(me_sp, service.getProvider());
                frame.dispose();
            }
        }));
        card.add(createDivider());

        // Price
        card.add(createInfoRow("Price", "$" + String.valueOf(service.getPrice())));
        card.add(Box.createRigidArea(new Dimension(0, 30))); // Spacer before buttons

        // ---------- Action Buttons Logic ----------
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        actionPanel.setBackground(Color.WHITE);
        actionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        boolean isAppliedByMe = service.getApplicants_userName().contains(me_sp.getUser_name());
        String applyText = isAppliedByMe ? "Applied" : "Apply";

        // Condition 1: Apply Button (Status = Incompleted)
        if(service.getStatus().equals("Incompleted")){
            JButton applyButton = createActionButton(applyText, new Color(59, 130, 246)); // Blue

            if(isAppliedByMe){
                applyButton.setBackground(new Color(148, 163, 184)); // Gray background for disabled
                applyButton.setText("Applied");
            }

            applyButton.addActionListener(e ->{
                //
                if(isAppliedByMe)
                    JOptionPane.showMessageDialog(frame, "You had applied to this job!", "Error", JOptionPane.ERROR_MESSAGE);
                else{
                    JOptionPane.showMessageDialog(frame, "Application Done", "Success", JOptionPane.INFORMATION_MESSAGE);
                    service.addApplicant(me_sp.getUser_name());
                    new Service_page_p(me_sp, service);
                    frame.dispose();
                }
            });

            actionPanel.add(applyButton);
        }

        if(isAppliedByMe && service.getStatus().equals("Incompleted")){
            JButton removeApplyButton = createActionButton("Remove Application", new Color(255, 36, 36)); // Blue

            removeApplyButton.addActionListener(e ->{
                JOptionPane.showMessageDialog(frame, "Removed Application", "Success", JOptionPane.INFORMATION_MESSAGE);
                service.removeApplicant(me_sp.getUser_name());
                new Service_page_p(me_sp, service);
                frame.dispose();
            });

            actionPanel.add(removeApplyButton);
        }

        // Condition 2: Deliver and Cancel (Mine & On-going)
        if(mine && service.getStatus().equals("On-going")){
            JButton deliverButton = createActionButton("Deliver", new Color(16, 185, 129)); // Green
            deliverButton.addActionListener(e -> {
                //
                me_sp.deliverForJob();
                new Service_page_p(me_sp);
                frame.dispose();
            });

            JButton cancelButton = createActionButton("Cancel Job", new Color(220, 38, 38)); // Red
            cancelButton.addActionListener(e -> {
                //
                me_sp.cancelJob();
                new Provider_account_p(me_sp);
                frame.dispose();
            });

            actionPanel.add(deliverButton);
            actionPanel.add(cancelButton);
        }

        // Condition 3: Handle Cancellation Request (Mine & On-request-cancellation)
        if(mine && service.getStatus().equals("On-request-cancellation")){
            JButton cancelCancellationRequest = createActionButton("Deny Cancellation", new Color(245, 158, 11)); // Amber/Orange
            cancelCancellationRequest.addActionListener(e -> {
                //
                me_sp.denyCancellationRequest();
                JOptionPane.showMessageDialog(frame, "You are continuing this job!", "Cancellation Request Denied", JOptionPane.INFORMATION_MESSAGE);
                new Service_page_p(me_sp, service);
                frame.dispose();
            });

            JButton acceptCancellationRequest = createActionButton("Accept Cancellation", new Color(220, 38, 38)); // Red
            acceptCancellationRequest.addActionListener(e -> {
                //
                me_sp.cancelJob();
                JOptionPane.showMessageDialog(frame, "You agreed to cancel this job!", "Cancellation Request Accepted", JOptionPane.INFORMATION_MESSAGE);
                new Service_page_p(me_sp, service);
                frame.dispose();
            });

            actionPanel.add(acceptCancellationRequest);
            actionPanel.add(cancelCancellationRequest);
        }

        card.add(actionPanel);

        contentPanel.add(titlePanel);
        contentPanel.add(card);
        contentPanel.add(Box.createVerticalGlue()); // Push content to top

        mainContainer.add(contentPanel, BorderLayout.CENTER);

        frame.add(mainContainer);
        frame.setVisible(true);
    }

    // ---------- UI Helper Methods ----------

    private static JPanel createInfoRow(String label, String value) {
        JPanel row = new JPanel(new BorderLayout(15, 0));
        row.setBackground(Color.WHITE);
        row.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 0));
        row.setMaximumSize(new Dimension(1000, 50));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel labelComp = new JLabel(label);
        labelComp.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        labelComp.setForeground(new Color(100, 116, 139));
        labelComp.setPreferredSize(new Dimension(150, 25));

        JLabel valueComp = new JLabel(value);
        valueComp.setFont(new Font("Segoe UI", Font.BOLD, 14));
        valueComp.setForeground(new Color(30, 41, 59));

        row.add(labelComp, BorderLayout.WEST);
        row.add(valueComp, BorderLayout.CENTER);

        return row;
    }

    // Helper for clickable links that look like rows (used for Client/Provider accounts)
    private static JPanel createLinkRow(String label, String value, java.awt.event.ActionListener action) {
        JPanel row = new JPanel(new BorderLayout(15, 0));
        row.setBackground(Color.WHITE);
        row.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        row.setMaximumSize(new Dimension(1000, 50));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel labelComp = new JLabel(label);
        labelComp.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        labelComp.setForeground(new Color(100, 116, 139));
        labelComp.setPreferredSize(new Dimension(150, 25));

        JButton linkBtn = new JButton(value);
        linkBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        linkBtn.setForeground(new Color(59, 130, 246)); // Link Blue
        linkBtn.setBackground(Color.WHITE);
        linkBtn.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        linkBtn.setContentAreaFilled(false);
        linkBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        linkBtn.setHorizontalAlignment(SwingConstants.LEFT);

        linkBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { linkBtn.setForeground(new Color(37, 99, 235)); }
            public void mouseExited(java.awt.event.MouseEvent evt) { linkBtn.setForeground(new Color(59, 130, 246)); }
        });

        linkBtn.addActionListener(action);

        row.add(labelComp, BorderLayout.WEST);
        row.add(linkBtn, BorderLayout.CENTER);

        return row;
    }

    private static JButton createActionButton(String text, Color baseColor) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(baseColor);
        btn.setBorder(BorderFactory.createEmptyBorder(12, 24, 12, 24));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { btn.setBackground(baseColor.darker()); }
            public void mouseExited(java.awt.event.MouseEvent evt) { btn.setBackground(baseColor); }
        });
        return btn;
    }

    private static JPanel createDivider() {
        JPanel divider = new JPanel();
        divider.setBackground(new Color(241, 245, 249));
        divider.setMaximumSize(new Dimension(1000, 1));
        divider.setPreferredSize(new Dimension(1000, 1));
        divider.setAlignmentX(Component.LEFT_ALIGNMENT);
        return divider;
    }

    private static JButton createNavButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setForeground(new Color(71, 85, 105));
        btn.setBackground(Color.WHITE);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setContentAreaFilled(false);

        if (text.equals("Logout")) {
            btn.setForeground(new Color(239, 68, 68));
        }

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setForeground(new Color(59, 130, 246));
                if (text.equals("Logout")) btn.setForeground(new Color(220, 38, 38));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setForeground(new Color(71, 85, 105));
                if (text.equals("Logout")) btn.setForeground(new Color(239, 68, 68));
            }
        });
        return btn;
    }

    private static JLabel createSeparator() {
        JLabel separator = new JLabel("|");
        separator.setForeground(new Color(220, 225, 230));
        separator.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return separator;
    }

    private static JPanel line(String labelText, String valueText) { return null; }
    private static JPanel buttonLine(String labelText, String buttonText, java.awt.event.ActionListener listener) { return null; }
    private static JPanel applicantLine(String labelText, String valueText, java.awt.event.ActionListener seeAllListener) { return null; }
    private static JLabel sectionLabel(String text) { return null; }
}
