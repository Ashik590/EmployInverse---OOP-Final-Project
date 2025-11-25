package UI.provider;

import UI.Login;
import logic.FileFunctions;
import logic.Service;
import logic.ServiceProvider;

import javax.swing.*;
import java.awt.*;

public class Notifications_p {
    private static ServiceProvider me_sp;

    public Notifications_p(ServiceProvider me) {
        me_sp = me;
        main(null);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("EmployInverse - Notifications");
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

        JLabel titleLabel = new JLabel("Notifications");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(30, 41, 59));

        JLabel subtitleLabel = new JLabel("Stay updated with your job status");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(100, 116, 139));

        JPanel titleBlock = new JPanel();
        titleBlock.setLayout(new BoxLayout(titleBlock, BoxLayout.Y_AXIS));
        titleBlock.setBackground(new Color(245, 247, 250));
        titleBlock.add(titleLabel);
        titleBlock.add(subtitleLabel);

        titlePanel.add(titleBlock, BorderLayout.WEST);

        // ---------- Notification Logic ----------
        Service currentJob = me_sp.getCurrentJob();
        JPanel notificationContent;

        if (currentJob != null) {
            // We have a notification - Show Card
            notificationContent = new JPanel();
            notificationContent.setLayout(new BoxLayout(notificationContent, BoxLayout.Y_AXIS));
            notificationContent.setBackground(new Color(245, 247, 250));
            notificationContent.add(createNotificationCard(currentJob, frame));
        } else {
            // No notification - Show Empty State
            notificationContent = createEmptyState();
        }

        contentPanel.add(titlePanel, BorderLayout.NORTH);
        contentPanel.add(notificationContent, BorderLayout.CENTER);

        mainContainer.add(navBar, BorderLayout.NORTH);
        mainContainer.add(contentPanel, BorderLayout.CENTER);

        frame.add(mainContainer);
        frame.setVisible(true);
    }

    // ---------- UI Helpers ----------

    private static JPanel createNotificationCard(Service job, JFrame parentFrame) {
        JPanel card = new JPanel(new BorderLayout(15, 0));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 225, 230), 1),
            BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Left Icon
        JLabel iconLabel = new JLabel("🔔"); // Bell Icon
        iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

        // Center Content
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(Color.WHITE);

        // Whether the service is on-going or client made a cancellation request

        String msgTitleText, msgBodyText;
        if(job.getStatus().equals("On-going")){
            msgTitleText = "New Job Assignment";
            msgBodyText = "You have been assigned a job by " + job.getClient_userName();
        }else{
            msgTitleText = "Cancellation Request";
            msgBodyText = "Client made cancellation request for the current job";
        }

        JLabel msgTitle = new JLabel(msgTitleText);
        msgTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        msgTitle.setForeground(new Color(30, 41, 59));

        JLabel msgBody = new JLabel(msgBodyText);
        msgBody.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        msgBody.setForeground(new Color(100, 116, 139));

        textPanel.add(msgTitle);
        textPanel.add(Box.createRigidArea(new Dimension(0, 4)));
        textPanel.add(msgBody);

        // Right Button
        JButton seeMoreBtn = new JButton("View Job Details");
        seeMoreBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        seeMoreBtn.setForeground(Color.WHITE);
        seeMoreBtn.setBackground(new Color(59, 130, 246));
        seeMoreBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        seeMoreBtn.setFocusPainted(false);
        seeMoreBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        seeMoreBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                seeMoreBtn.setBackground(new Color(37, 99, 235));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                seeMoreBtn.setBackground(new Color(59, 130, 246));
            }
        });

        seeMoreBtn.addActionListener(e -> {
            new Service_page_p(me_sp);
            parentFrame.dispose();
        });

        card.add(iconLabel, BorderLayout.WEST);
        card.add(textPanel, BorderLayout.CENTER);
        card.add(seeMoreBtn, BorderLayout.EAST);

        return card;
    }

    private static JPanel createEmptyState() {
        JPanel emptyPanel = new JPanel();
        emptyPanel.setLayout(new BoxLayout(emptyPanel, BoxLayout.Y_AXIS));
        emptyPanel.setBackground(new Color(245, 247, 250)); // Match main background

        // Icon
        JLabel iconLabel = new JLabel("✅");
        iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 64));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Message
        JLabel messageLabel = new JLabel("All caught up");
        messageLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        messageLabel.setForeground(new Color(100, 116, 139));
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Subtitle
        JLabel subtitleLabel = new JLabel("You have no job at this time.");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(148, 163, 184));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        emptyPanel.add(Box.createVerticalGlue());
        emptyPanel.add(iconLabel);
        emptyPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        emptyPanel.add(messageLabel);
        emptyPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        emptyPanel.add(subtitleLabel);
        emptyPanel.add(Box.createVerticalGlue());

        return emptyPanel;
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
