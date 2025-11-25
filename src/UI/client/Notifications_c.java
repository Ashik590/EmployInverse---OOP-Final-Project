package UI.client;

import UI.Login;
import logic.Client;
import logic.Service;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Notifications_c {
    private static Client me_client;

    public Notifications_c(Client me) {
        me_client = me;
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

        JLabel nameLabel = new JLabel("Client: " + me_client.getUser_name());
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        nameLabel.setForeground(new Color(71, 85, 105));

        JLabel balanceLabel = new JLabel("Balance: $" + me_client.getBalance());
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

        String[] navButtons = {"All Job Posts", "Clients", "Providers", "My Account", "My Posts", "Notifications", "Logout"};
        for (String text : navButtons) {
            JButton btn = createNavButton(text);
            btn.addActionListener(e -> {
                System.out.println(text + " clicked");
                switch (text) {
                    case "All Job Posts" -> new All_services_c(me_client);
                    case "Clients" -> new Clients_page_c(me_client);
                    case "Providers" -> new Providers_page_c(me_client);
                    case "My Account" -> new Client_account_c(me_client);
                    case "My Posts" -> new All_services_c(me_client.getOfferedJobs(), me_client, "My", me_client);
                    case "Notifications" -> new Notifications_c(me_client);
                    case "Logout" -> new Login();
                }
                frame.dispose();
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

        JLabel subtitleLabel = new JLabel("Stay updated with your job deliveries");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(100, 116, 139));

        titlePanel.add(titleLabel, BorderLayout.NORTH);
        titlePanel.add(subtitleLabel, BorderLayout.CENTER);

        // ---------- Get delivered jobs ----------
        ArrayList<Service> deliveredJobs = new ArrayList<>();
        ArrayList<Service> all_offered_jobs = me_client.getOfferedJobs();

        for (Service allOfferedJob : all_offered_jobs) {
            if (allOfferedJob.getStatus().equals("Delivered")) {
                deliveredJobs.add(allOfferedJob);
            }
        }

        // ---------- Notifications Content ----------
        JPanel notificationsContent;

        if (deliveredJobs.isEmpty()) {
            // Empty state
            notificationsContent = createEmptyState();
        } else {
            // Notifications list
            notificationsContent = createNotificationsList(deliveredJobs, frame);
        }

        contentPanel.add(titlePanel, BorderLayout.NORTH);
        contentPanel.add(notificationsContent, BorderLayout.CENTER);

        mainContainer.add(navBar, BorderLayout.NORTH);
        mainContainer.add(contentPanel, BorderLayout.CENTER);

        frame.add(mainContainer);
        frame.setVisible(true);
    }

    private static JPanel createEmptyState() {
        JPanel emptyPanel = new JPanel();
        emptyPanel.setLayout(new BoxLayout(emptyPanel, BoxLayout.Y_AXIS));
        emptyPanel.setBackground(Color.WHITE);
        emptyPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 225, 230), 1),
            BorderFactory.createEmptyBorder(80, 40, 80, 40)
        ));

        // Icon
        JLabel iconLabel = new JLabel("🔔");
        iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 64));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Message
        JLabel messageLabel = new JLabel("No Notifications");
        messageLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        messageLabel.setForeground(new Color(100, 116, 139));
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Subtitle
        JLabel subtitleLabel = new JLabel("You're all caught up! Check back later for updates.");
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

    private static JPanel createNotificationsList(ArrayList<Service> deliveredJobs, JFrame frame) {
        JPanel listContainer = new JPanel(new BorderLayout());
        listContainer.setBackground(new Color(245, 247, 250));

        JPanel notificationListPanel = new JPanel();
        notificationListPanel.setLayout(new BoxLayout(notificationListPanel, BoxLayout.Y_AXIS));
        notificationListPanel.setBackground(new Color(245, 247, 250));

        for (int i = 0; i < deliveredJobs.size(); i++) {
            Service the_service = deliveredJobs.get(i);
            JPanel notificationCard = createNotificationCard(the_service, i + 1, frame);
            notificationListPanel.add(notificationCard);
            notificationListPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        }

        JScrollPane scrollPane = new JScrollPane(notificationListPanel);
        scrollPane.setBackground(new Color(245, 247, 250));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        listContainer.add(scrollPane, BorderLayout.CENTER);
        return listContainer;
    }

    private static JPanel createNotificationCard(Service service, int number, JFrame frame) {
        JPanel card = new JPanel(new BorderLayout(15, 0));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 225, 230), 1),
            BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));

        // Left side - Number badge
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(Color.WHITE);

        JLabel numberBadge = new JLabel(String.valueOf(number));
        numberBadge.setFont(new Font("Segoe UI", Font.BOLD, 16));
        numberBadge.setForeground(new Color(59, 130, 246));
        numberBadge.setHorizontalAlignment(SwingConstants.CENTER);
        numberBadge.setPreferredSize(new Dimension(40, 40));
        numberBadge.setMaximumSize(new Dimension(40, 40));
        numberBadge.setOpaque(true);
        numberBadge.setBackground(new Color(239, 246, 255));
        numberBadge.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        leftPanel.add(numberBadge);
        leftPanel.add(Box.createVerticalGlue());

        // Center - Notification content
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.WHITE);

        String providerName = service.getProvider_userName();
        String jobTitle = service.getTitle();

        JLabel titleLabel = new JLabel("Job Delivered");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        titleLabel.setForeground(new Color(30, 41, 59));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel messageLabel = new JLabel(
            "<html><b>" + providerName + "</b> has delivered the job: <i>\"" + jobTitle + "\"</i></html>"
        );
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        messageLabel.setForeground(new Color(71, 85, 105));
        messageLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        centerPanel.add(titleLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        centerPanel.add(messageLabel);
        centerPanel.add(Box.createVerticalGlue());

        // Right side - Action button
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(Color.WHITE);

        JButton seeMoreBtn = new JButton("View Details");
        seeMoreBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        seeMoreBtn.setForeground(Color.WHITE);
        seeMoreBtn.setBackground(new Color(59, 130, 246));
        seeMoreBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        seeMoreBtn.setFocusPainted(false);
        seeMoreBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        seeMoreBtn.setMaximumSize(new Dimension(140, 38));

        seeMoreBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                seeMoreBtn.setBackground(new Color(37, 99, 235));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                seeMoreBtn.setBackground(new Color(59, 130, 246));
            }
        });

        seeMoreBtn.addActionListener(e -> {
            new Service_page_c(me_client, service, true);
            frame.dispose();
        });

        rightPanel.add(seeMoreBtn);
        rightPanel.add(Box.createVerticalGlue());

        card.add(leftPanel, BorderLayout.WEST);
        card.add(centerPanel, BorderLayout.CENTER);
        card.add(rightPanel, BorderLayout.EAST);

        return card;
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
