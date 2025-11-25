package UI.provider;

import UI.Login;
import logic.FileFunctions;
import logic.Service;
import logic.ServiceProvider;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Provider_account_p {

    private static ServiceProvider me_sp;
    private static ServiceProvider provider;
    private static boolean mine;

    public Provider_account_p(ServiceProvider me, ServiceProvider sp) {
        provider = sp;
        me_sp = me;

        mine = me.getUser_name().equals(sp.getUser_name());

        main(null);
    }
    public Provider_account_p(ServiceProvider me) {
        me_sp = provider = me;
        mine = true;

        main(null);
    }

    public static void main(String[] args) {
//        me_sp = FileFunctions.searchProvider("hakim");
//        mine = true;
//        provider = me_sp;

        // Use a dynamic title
        String frameTitle = mine ? "EmployInverse - My Account" : "EmployInverse - Provider Account";
        JFrame frame = new JFrame(frameTitle);
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

        String[] navButtons = { "All Job Posts", "Clients", "Providers", "My Account", "My Job", "Notifications",
            "Logout" };
        for (String text : navButtons) {
            JButton btn = createNavButton(text);
            // Action listeners from original code
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
                        if (me_sp.getCurrentJobID().equals("None"))
                            JOptionPane.showMessageDialog(frame, "I have no job!", "Error", JOptionPane.ERROR_MESSAGE);
                        else {
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

        // ---------- Content Area ----------
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(245, 247, 250));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // Title Section
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(245, 247, 250));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 25, 0));

        String headerName = mine ? me_sp.getName() + " (Me)" : provider.getName();
        String headerSubtitle = mine ? "Manage your account settings and stats" : "Service Provider profile details";

        JLabel titleLabel = new JLabel(headerName);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(30, 41, 59));

        JLabel subtitleLabel = new JLabel(headerSubtitle);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(100, 116, 139));

        titlePanel.add(titleLabel, BorderLayout.NORTH);
        titlePanel.add(subtitleLabel, BorderLayout.CENTER);

        // ---------- Main Content Grid (Two-Column Layout) ----------
        JPanel gridPanel = new JPanel(new GridBagLayout());
        gridPanel.setBackground(new Color(245, 247, 250));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 0, 20, 20);

        // Account Info Card (Left Column)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 0.6;
        gridPanel.add(createAccountInfoCard(frame), gbc);

        // Job Stats Card (Right Column)
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 20, 0);
        gridPanel.add(createJobStatsCard(frame), gbc);

        contentPanel.add(titlePanel, BorderLayout.NORTH);
        contentPanel.add(gridPanel, BorderLayout.CENTER);

        mainContainer.add(navBar, BorderLayout.NORTH);
        mainContainer.add(contentPanel, BorderLayout.CENTER);

        frame.add(mainContainer);
        frame.setVisible(true);
    }

    // ---------- UI Helper Methods ----------

    private static JPanel createAccountInfoCard(JFrame parentFrame) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 225, 230), 1),
            BorderFactory.createEmptyBorder(25, 30, 25, 30)
        ));

        // Card Title
        String cardTitleText = mine ? "Account Information" : "Provider Details";
        JLabel cardTitle = new JLabel(cardTitleText);
        cardTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        cardTitle.setForeground(new Color(30, 41, 59));
        cardTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(cardTitle);
        card.add(Box.createRigidArea(new Dimension(0, 20)));

        // Account details
        card.add(createInfoRow("Name", provider.getName()));
        card.add(createDivider());
        card.add(createInfoRow("Phone", provider.getPhone()));
        card.add(createDivider());
        card.add(createInfoRow("Email", provider.getEmail()));
        card.add(createDivider());
        card.add(createInfoRow("Username", provider.getUser_name()));
        card.add(createDivider());
        card.add(createInfoRow("Account Type", "Service Provider"));
        card.add(createDivider());
        card.add(createInfoRow("Balance", "$" + String.valueOf(provider.getBalance())));
        card.add(createDivider());

        // Current Job Row
        JPanel jobRow = createInfoRow("Current Job", "");

        String jobBtnText = provider.getCurrentJob() == null ? "None" : provider.getCurrentJob().getTitle();
        JButton jobBtn = new JButton(jobBtnText);

        // Style the button to look like a link or simple label
        jobBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jobBtn.setForeground(provider.getCurrentJob() == null ? new Color(100, 116, 139) : new Color(59, 130, 246));
        jobBtn.setBackground(Color.WHITE);
        jobBtn.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        jobBtn.setContentAreaFilled(false);
        jobBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jobBtn.setHorizontalAlignment(SwingConstants.LEFT);

        // Current Job button action
        jobBtn.addActionListener(e -> {
            if(provider.getCurrentJob() != null){
                new Service_page_p(me_sp, provider.getCurrentJob());
                parentFrame.dispose();
            }
            else
                JOptionPane.showMessageDialog(parentFrame, "He has no job right now!", "Error", JOptionPane.ERROR_MESSAGE);
        });

        jobRow.add(jobBtn, BorderLayout.CENTER);
        card.add(jobRow);

        return card;
    }

    private static JPanel createJobStatsCard(JFrame parentFrame) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 225, 230), 1),
            BorderFactory.createEmptyBorder(25, 30, 25, 30)
        ));

        // Card Title
        JLabel cardTitle = new JLabel("Job Statistics");
        cardTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        cardTitle.setForeground(new Color(30, 41, 59));
        cardTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(cardTitle);
        card.add(Box.createRigidArea(new Dimension(0, 20)));

        // --- Completed Jobs Section (Styled Counter) ---
        JPanel statsDisplay = new JPanel();
        statsDisplay.setLayout(new BoxLayout(statsDisplay, BoxLayout.Y_AXIS));
        statsDisplay.setBackground(new Color(239, 246, 255));
        statsDisplay.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        statsDisplay.setAlignmentX(Component.LEFT_ALIGNMENT);
        statsDisplay.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150)); // Limit vertical stretch

        JLabel statsIcon = new JLabel("✅");
        statsIcon.setFont(new Font("Segoe UI", Font.PLAIN, 32));
        statsIcon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel jobCount = new JLabel(String.valueOf(provider.getCompletedJobsID().size()));
        jobCount.setFont(new Font("Segoe UI", Font.BOLD, 36));
        jobCount.setForeground(new Color(16, 185, 129));
        jobCount.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel jobLabel = new JLabel("Jobs Completed");
        jobLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jobLabel.setForeground(new Color(71, 85, 105));
        jobLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        statsDisplay.add(statsIcon);
        statsDisplay.add(Box.createRigidArea(new Dimension(0, 0)));
        statsDisplay.add(jobCount);
        statsDisplay.add(Box.createRigidArea(new Dimension(0, 0)));
        statsDisplay.add(jobLabel);

        card.add(statsDisplay);
        card.add(Box.createRigidArea(new Dimension(0, 50)));

        // --- Applied Jobs Section (Primary Button) ---
        ArrayList<Service> appliedJobs = provider.getAppliedJobs();

        JLabel appliedLabel = new JLabel("Applied Jobs: " + appliedJobs.size());
        appliedLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        appliedLabel.setForeground(new Color(30, 41, 59));
        appliedLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(appliedLabel);
        card.add(Box.createRigidArea(new Dimension(0, 10)));

        JButton seeAppliedJobBtn = new JButton("View All Applied Jobs");
        seeAppliedJobBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        seeAppliedJobBtn.setForeground(Color.WHITE);
        seeAppliedJobBtn.setBackground(new Color(59, 130, 246));
        seeAppliedJobBtn.setBorder(BorderFactory.createEmptyBorder(12, 24, 12, 24));
        seeAppliedJobBtn.setFocusPainted(false);
        seeAppliedJobBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        seeAppliedJobBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        seeAppliedJobBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));

        seeAppliedJobBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                seeAppliedJobBtn.setBackground(new Color(37, 99, 235));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                seeAppliedJobBtn.setBackground(new Color(59, 130, 246));
            }
        });

        // See Applied Jobs button action
        seeAppliedJobBtn.addActionListener(e -> {
            new All_services_p(appliedJobs, me_sp);
            parentFrame.dispose();
        });

        card.add(seeAppliedJobBtn);

        // --- Completed Jobs Button (Secondary Action) ---
        card.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton seeAllCompletedBtn = new JButton("View All Completed Jobs");
        seeAllCompletedBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        seeAllCompletedBtn.setForeground(new Color(59, 130, 246));
        seeAllCompletedBtn.setBackground(Color.WHITE);
        seeAllCompletedBtn.setBorder(BorderFactory.createLineBorder(new Color(59, 130, 246)));
        seeAllCompletedBtn.setFocusPainted(false);
        seeAllCompletedBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        seeAllCompletedBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        seeAllCompletedBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));

        seeAllCompletedBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                seeAllCompletedBtn.setBackground(new Color(239, 246, 255));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                seeAllCompletedBtn.setBackground(Color.WHITE);
            }
        });

        // See All Jobs button action
        seeAllCompletedBtn.addActionListener(e -> {
            new All_services_p(provider.getCompletedJobs(), me_sp);
            parentFrame.dispose();
        });

        card.add(seeAllCompletedBtn);

        return card;
    }

    // Helper methods must be present to compile

    private static JPanel createInfoRow(String label, String value) {
        JPanel row = new JPanel(new BorderLayout(15, 0));
        row.setBackground(Color.WHITE);
        row.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 0));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        JLabel labelComp = new JLabel(label);
        labelComp.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        labelComp.setForeground(new Color(100, 116, 139));
        labelComp.setPreferredSize(new Dimension(120, 25));

        JLabel valueComp = new JLabel(value);
        valueComp.setFont(new Font("Segoe UI", Font.BOLD, 14));
        valueComp.setForeground(new Color(30, 41, 59));

        row.add(labelComp, BorderLayout.WEST);
        row.add(valueComp, BorderLayout.CENTER);

        return row;
    }

    private static JPanel createDivider() {
        JPanel divider = new JPanel();
        divider.setBackground(new Color(241, 245, 249));
        divider.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        divider.setPreferredSize(new Dimension(Integer.MAX_VALUE, 1));
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
                if (text.equals("Logout")) {
                    btn.setForeground(new Color(220, 38, 38));
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setForeground(new Color(71, 85, 105));
                if (text.equals("Logout")) {
                    btn.setForeground(new Color(239, 68, 68));
                }
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
}
