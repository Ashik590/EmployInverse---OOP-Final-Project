package UI.client;

import UI.Login;
import logic.Client;

import javax.swing.*;
import java.awt.*;

public class Create_service {
    private static Client me_client;

    public Create_service(Client me){
        me_client = me;
        main(null);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("EmployInverse - Create Job Post");
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

        JLabel titleLabel = new JLabel("Create New Job Post");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(30, 41, 59));

        JLabel subtitleLabel = new JLabel("Fill in the details for your job posting");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(100, 116, 139));

        titlePanel.add(titleLabel, BorderLayout.NORTH);
        titlePanel.add(subtitleLabel, BorderLayout.CENTER);

        // ---------- Form Card ----------
        JPanel formCard = new JPanel();
        formCard.setLayout(null);
        formCard.setBackground(Color.WHITE);
        formCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 225, 230), 1),
            BorderFactory.createEmptyBorder(40, 50, 40, 50)
        ));
        formCard.setPreferredSize(new Dimension(800, 500));

        // Title field
        JLabel titleFieldLabel = new JLabel("Job Title");
        titleFieldLabel.setBounds(20, 20, 760, 20);
        titleFieldLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        titleFieldLabel.setForeground(new Color(51, 65, 85));
        formCard.add(titleFieldLabel);

        JTextField titleField = new JTextField();
        titleField.setBounds(20, 50, 760, 40);
        titleField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(203, 213, 225), 1),
            BorderFactory.createEmptyBorder(5, 12, 5, 12)
        ));
        formCard.add(titleField);

        // Description field
        JLabel descriptionLabel = new JLabel("Job Description");
        descriptionLabel.setBounds(20, 110, 760, 20);
        descriptionLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        descriptionLabel.setForeground(new Color(51, 65, 85));
        formCard.add(descriptionLabel);

        JTextArea descriptionArea = new JTextArea();
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        descriptionArea.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        scrollPane.setBounds(20, 140, 760, 150);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(203, 213, 225), 1));
        formCard.add(scrollPane);

        // Price field
        JLabel priceLabel = new JLabel("Price ($)");
        priceLabel.setBounds(20, 310, 300, 20);
        priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        priceLabel.setForeground(new Color(51, 65, 85));
        formCard.add(priceLabel);

        JTextField priceField = new JTextField();
        priceField.setBounds(20, 340, 200, 40);
        priceField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        priceField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(203, 213, 225), 1),
            BorderFactory.createEmptyBorder(5, 12, 5, 12)
        ));
        formCard.add(priceField);

        // Create Button
        JButton createBtn = new JButton("Create Job Post");
        createBtn.setBounds(20, 410, 250, 45);
        createBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        createBtn.setForeground(Color.WHITE);
        createBtn.setBackground(new Color(16, 185, 129));
        createBtn.setBorder(BorderFactory.createEmptyBorder());
        createBtn.setFocusPainted(false);
        createBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        formCard.add(createBtn);

        // Hover effect for create button
        createBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                createBtn.setBackground(new Color(5, 150, 105));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                createBtn.setBackground(new Color(16, 185, 129));
            }
        });

        // Create button listener
        createBtn.addActionListener(e -> {
            String titleText = titleField.getText().trim();
            String description = descriptionArea.getText().trim();
            String priceText = priceField.getText().trim();

            if (titleText.isEmpty() || description.isEmpty() || priceText.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int price;
            try {
                price = Integer.parseInt(priceText);
                if (price < 0) {
                    JOptionPane.showMessageDialog(frame, "Price cannot be negative!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid number for price!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            me_client.createService(titleText, description, price, true);

            JOptionPane.showMessageDialog(frame,
                "Job Created Successfully!\nTitle: " + titleText + "\nDescription: " + description + "\nPrice: $" + price,
                "Success",
                JOptionPane.INFORMATION_MESSAGE);

            // Clear input fields after creation
            titleField.setText("");
            descriptionArea.setText("");
            priceField.setText("");

            new Client_account_c(me_client);
            frame.dispose();
        });

        // Center the form card
        JPanel centerWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        centerWrapper.setBackground(new Color(245, 247, 250));
        centerWrapper.add(formCard);

        contentPanel.add(titlePanel, BorderLayout.NORTH);
        contentPanel.add(centerWrapper, BorderLayout.CENTER);

        mainContainer.add(navBar, BorderLayout.NORTH);
        mainContainer.add(contentPanel, BorderLayout.CENTER);

        frame.add(mainContainer);
        frame.setVisible(true);
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
