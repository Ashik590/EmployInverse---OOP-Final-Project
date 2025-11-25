package UI.provider;

import UI.Login;
import UI.client.Client_account_c;
import UI.client.Provider_account_c;
import logic.Client;
import logic.FileFunctions;
import logic.Service;
import logic.ServiceProvider;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class All_services_p {
    private static ArrayList<Service> all_services;
    private static ServiceProvider me_sp;
    private static String msg;
    private static ServiceProvider related_provider;
    private static Client owner_client;

    public All_services_p(ArrayList<Service> all_servs, ServiceProvider me, String message, ServiceProvider related_sp) {
        all_services = all_servs;
        me_sp = me;
        related_provider = related_sp;
        owner_client = null;
        msg = message;

        if(me.getUser_name().equals(related_sp.getUser_name())) {
            String newMsg = msg;
            msg = "(Me) ";
            msg += newMsg;
        }

        main(null);
    }
    public All_services_p(ArrayList<Service> all_servs, ServiceProvider me, String message, Client owner_client_) {
        all_services = all_servs;
        me_sp = me;
        owner_client = owner_client_;
        related_provider = null;
        msg = message;

        main(null);
    }

    public All_services_p(ServiceProvider me) {
        all_services = FileFunctions.getAllServices();
        me_sp = me;
        msg = "";
        related_provider = null;
        owner_client = null;

        main(null);
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("EmployInverse - All Job Posts");
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
            BorderFactory.createEmptyBorder(15, 30, 15, 30)));

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

        // Title Section with button
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(245, 247, 250));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 25, 0));

        JPanel titleLeft = new JPanel();
        titleLeft.setLayout(new BoxLayout(titleLeft, BoxLayout.Y_AXIS));
        titleLeft.setBackground(new Color(245, 247, 250));

        JLabel titleLabel = new JLabel((msg.isEmpty() ? "All" :msg)+ " Job Posts");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(30, 41, 59));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitleLabel = new JLabel(
            all_services.size() + " job" + (all_services.size() != 1 ? "s" : "") + " found");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(100, 116, 139));
        subtitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        titleLeft.add(titleLabel);
        titleLeft.add(Box.createRigidArea(new Dimension(0, 5)));
        titleLeft.add(subtitleLabel);

        // Extra button to go back (if needed)
        if(!msg.isEmpty()) {
            JButton goBackBtn = new JButton("Go Back");
            goBackBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
            goBackBtn.setForeground(Color.WHITE);
            goBackBtn.setBackground(new Color(59, 130, 246));
            goBackBtn.setBorder(BorderFactory.createEmptyBorder(12, 24, 12, 24));
            goBackBtn.setFocusPainted(false);
            goBackBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

            goBackBtn.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    goBackBtn.setBackground(new Color(37, 99, 235));
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    goBackBtn.setBackground(new Color(59, 130, 246));
                }
            });

            goBackBtn.addActionListener(e -> {
                if(owner_client != null) {
                    new Client_account_p(me_sp, owner_client);
                }else if(related_provider != null){
                    new Provider_account_p(me_sp, related_provider);
                }
                frame.dispose();
            });

            titlePanel.add(goBackBtn, BorderLayout.EAST);
        }

        titlePanel.add(titleLeft, BorderLayout.WEST);

        // ---------- Clients Content ----------
        JPanel applicantsContent;

        if (all_services.isEmpty()) {
            // Empty state
            applicantsContent = createEmptyState();
        } else {
            // Applicants list
            applicantsContent = createApplicantsList(frame);
        }

        contentPanel.add(titlePanel, BorderLayout.NORTH);
        contentPanel.add(applicantsContent, BorderLayout.CENTER);

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
            BorderFactory.createEmptyBorder(80, 40, 80, 40)));

        // Icon
        JLabel iconLabel = new JLabel("👥");
        iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 64));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Message
        JLabel messageLabel = new JLabel("No job found yet");
        messageLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        messageLabel.setForeground(new Color(100, 116, 139));
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Subtitle
        JLabel subtitleLabel = new JLabel("Don't waste time to be here");
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

    private static JPanel createApplicantsList(JFrame frame) {
        JPanel listContainer = new JPanel(new BorderLayout());
        listContainer.setBackground(new Color(245, 247, 250));

        JPanel servicesListPanel = new JPanel();
        servicesListPanel.setLayout(new BoxLayout(servicesListPanel, BoxLayout.Y_AXIS));
        servicesListPanel.setBackground(new Color(245, 247, 250));

        for (int i = 0; i < all_services.size(); i++) {
            Service service = all_services.get(i);
            JPanel serviceCard = createApplicantCard(service, i + 1, frame);
            servicesListPanel.add(serviceCard);
            servicesListPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        }

        JScrollPane scrollPane = new JScrollPane(servicesListPanel);
        scrollPane.setBackground(new Color(245, 247, 250));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        listContainer.add(scrollPane, BorderLayout.CENTER);
        return listContainer;
    }

    private static JPanel createApplicantCard(Service service, int number, JFrame parentFrame) {
        JPanel card = new JPanel(new BorderLayout(15, 0));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 225, 230), 1),
            BorderFactory.createEmptyBorder(20, 25, 20, 25)));
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

        // Center - client info
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.WHITE);

        JLabel nameLabel = new JLabel(service.getTitle());
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        nameLabel.setForeground(new Color(30, 41, 59));
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel usernameLabel = new JLabel("@" + service.getStatus());
        usernameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        usernameLabel.setForeground(new Color(100, 116, 139));
        usernameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        centerPanel.add(nameLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        centerPanel.add(usernameLabel);
        centerPanel.add(Box.createVerticalGlue());

        // Right side - Action button
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(Color.WHITE);

        JButton viewDetailsBtn = new JButton("View Details");
        viewDetailsBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        viewDetailsBtn.setForeground(Color.WHITE);
        viewDetailsBtn.setBackground(new Color(59, 130, 246));
        viewDetailsBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        viewDetailsBtn.setFocusPainted(false);
        viewDetailsBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        viewDetailsBtn.setMaximumSize(new Dimension(140, 38));

        viewDetailsBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                viewDetailsBtn.setBackground(new Color(37, 99, 235));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                viewDetailsBtn.setBackground(new Color(59, 130, 246));
            }
        });

        viewDetailsBtn.addActionListener(e -> {
            new Service_page_p(me_sp, service);
            parentFrame.dispose();
        });

        rightPanel.add(viewDetailsBtn);
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
