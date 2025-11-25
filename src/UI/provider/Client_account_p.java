package UI.provider;

import UI.Login;
import logic.Client;
import logic.ServiceProvider;

import javax.swing.*;
import java.awt.*;

public class Client_account_p{

    private static ServiceProvider me_sp;
    private static Client client;


    public Client_account_p(ServiceProvider me, Client client_) {
        me_sp = me;
        client = client_;

        main(null);
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("EmployInverse - Client Account");
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

        JLabel nameLabel = new JLabel("Client: " + me_sp.getUser_name());
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        nameLabel.setForeground(new Color(71, 85, 105));

        JLabel balanceLabel = new JLabel("Balance: $" + me_sp.getBalance());
        balanceLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        balanceLabel.setForeground(new Color(16, 185, 129));

        // Forward and Backward Buttons st
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
            btn.addActionListener(e -> {
                switch (text) {
                    case "All Job Posts" -> {
                        new All_services_p(me_sp);
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

        JLabel titleLabel = new JLabel(client.getName());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(30, 41, 59));

        JLabel subtitleLabel = new JLabel("Client profile details");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(100, 116, 139));

        titlePanel.add(titleLabel, BorderLayout.NORTH);
        titlePanel.add(subtitleLabel, BorderLayout.CENTER);

        // ---------- Main Content Grid ----------
        JPanel gridPanel = new JPanel(new GridBagLayout());
        gridPanel.setBackground(new Color(245, 247, 250));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 0, 20, 20);

        // Account Info Card
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 0.6;
        gridPanel.add(createAccountInfoCard(), gbc);

        // Job Stats Card
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

    private static JPanel createAccountInfoCard() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 225, 230), 1),
            BorderFactory.createEmptyBorder(25, 30, 25, 30)
        ));

        // Card Title
        JLabel cardTitle = new JLabel("Account Information");
        cardTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        cardTitle.setForeground(new Color(30, 41, 59));
        cardTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(cardTitle);
        card.add(Box.createRigidArea(new Dimension(0, 20)));

        // Account details
        card.add(createInfoRow("Name", client.getName()));
        card.add(createDivider());
        card.add(createInfoRow("Phone", client.getPhone()));
        card.add(createDivider());
        card.add(createInfoRow("Email", client.getEmail()));
        card.add(createDivider());
        card.add(createInfoRow("Username", client.getUser_name()));
        card.add(createDivider());
        card.add(createInfoRow("Account Type", "Client"));

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

        // Stats display with icon
        JPanel statsDisplay = new JPanel();
        statsDisplay.setLayout(new BoxLayout(statsDisplay, BoxLayout.Y_AXIS));
        statsDisplay.setBackground(new Color(239, 246, 255));
        statsDisplay.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        statsDisplay.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel statsIcon = new JLabel("📊");
        statsIcon.setFont(new Font("Segoe UI", Font.PLAIN, 32));
        statsIcon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel jobCount = new JLabel(String.valueOf(client.getOfferedJobsID().size()));
        jobCount.setFont(new Font("Segoe UI", Font.BOLD, 36));
        jobCount.setForeground(new Color(59, 130, 246));
        jobCount.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel jobLabel = new JLabel("Jobs Posted");
        jobLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jobLabel.setForeground(new Color(71, 85, 105));
        jobLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        statsDisplay.add(statsIcon);
        statsDisplay.add(Box.createRigidArea(new Dimension(0, 10)));
        statsDisplay.add(jobCount);
        statsDisplay.add(Box.createRigidArea(new Dimension(0, 5)));
        statsDisplay.add(jobLabel);

        card.add(statsDisplay);
        card.add(Box.createRigidArea(new Dimension(0, 20)));

        // See All Jobs Button
        JButton seeAllBtn = new JButton("View All Jobs");
        seeAllBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        seeAllBtn.setForeground(Color.WHITE);
        seeAllBtn.setBackground(new Color(59, 130, 246));
        seeAllBtn.setBorder(BorderFactory.createEmptyBorder(12, 24, 12, 24));
        seeAllBtn.setFocusPainted(false);
        seeAllBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        seeAllBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        seeAllBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));

        seeAllBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                seeAllBtn.setBackground(new Color(37, 99, 235));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                seeAllBtn.setBackground(new Color(59, 130, 246));
            }
        });

        seeAllBtn.addActionListener(e -> {
            new All_services_p(client.getOfferedJobs(), me_sp, client.getUser_name()+"'s", client);
            parentFrame.dispose();
        });

        card.add(seeAllBtn);

        return card;
    }

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
