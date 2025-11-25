package UI.client;

import logic.Client;
import logic.Service;
import logic.ServiceProvider;

import javax.swing.*;
import java.awt.*;

public class Applicant_pop {
    private static Client me_client;
    private static ServiceProvider applicant;
    private static Service service;

    public Applicant_pop(Client me, ServiceProvider applicant_sp, Service service_) {
        me_client = me;
        applicant = applicant_sp;
        service = service_;

        main(null);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Applicant Details");
        frame.setSize(480, 280);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        // Main panel with background
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(new Color(245, 247, 250));

        // Content card panel
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(null);
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setPreferredSize(new Dimension(420, 220));
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 225, 230), 1),
            BorderFactory.createEmptyBorder(25, 30, 25, 30)
        ));

        // Title
        JLabel titleLabel = new JLabel("Applicant Information");
        titleLabel.setBounds(0, 5, 420, 30);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(30, 41, 59));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        cardPanel.add(titleLabel);

        // Divider
        JPanel divider = new JPanel();
        divider.setBounds(0, 38, 360, 1);
        divider.setBackground(new Color(226, 232, 240));
        cardPanel.add(divider);

        // User name section
        JPanel userInfoPanel = new JPanel(new BorderLayout(10, 0));
        userInfoPanel.setBounds(5, 55, 410, 30);
        userInfoPanel.setBackground(new Color(239, 246, 255));
        userInfoPanel.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));

        JLabel userNameLabel = new JLabel("Username:");
        userNameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        userNameLabel.setForeground(new Color(71, 85, 105));

        JLabel userNameField = new JLabel(applicant.getUser_name());
        userNameField.setFont(new Font("Segoe UI", Font.BOLD, 14));
        userNameField.setForeground(new Color(30, 41, 59));

        userInfoPanel.add(userNameLabel, BorderLayout.WEST);
        userInfoPanel.add(userNameField, BorderLayout.CENTER);
        cardPanel.add(userInfoPanel);

        // Buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        buttonsPanel.setBounds(0, 115, 360, 45);
        buttonsPanel.setBackground(Color.WHITE);

        // Approve Button
        JButton approveBtn = new JButton("Approve");
        approveBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        approveBtn.setForeground(Color.WHITE);
        approveBtn.setBackground(new Color(16, 185, 129));
        approveBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        approveBtn.setFocusPainted(false);
        approveBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        approveBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                approveBtn.setBackground(new Color(5, 150, 105));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                approveBtn.setBackground(new Color(16, 185, 129));
            }
        });

        approveBtn.addActionListener(e -> {
            int status = me_client.chooseProvider(applicant, service);
            if (status == 1) {
                JOptionPane.showMessageDialog(frame, "The Provider has been chosen for the Job!", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                new Service_page_c(me_client, service);
            } else if (status == 2) {
                JOptionPane.showMessageDialog(frame, "He is busy right now", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else if (status == 3) {
                JOptionPane.showMessageDialog(frame, "You don't have enough money!", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else if (status == 4) {
                JOptionPane.showMessageDialog(frame, "The Service's status is on-going!", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            frame.dispose();
        });

        // Remove Button
        JButton removeBtn = new JButton("Remove");
        removeBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        removeBtn.setForeground(Color.WHITE);
        removeBtn.setBackground(new Color(239, 68, 68));
        removeBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        removeBtn.setFocusPainted(false);
        removeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        removeBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                removeBtn.setBackground(new Color(220, 38, 38));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                removeBtn.setBackground(new Color(239, 68, 68));
            }
        });

        removeBtn.addActionListener(e -> {
            me_client.deleteApplicant(service, applicant);
            JOptionPane.showMessageDialog(frame, "The Provider has been removed from the applicants!", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
            new Service_page_c(me_client, service);
            frame.dispose();
        });

        // Profile Button
        JButton profileBtn = new JButton("View Profile");
        profileBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        profileBtn.setForeground(Color.WHITE);
        profileBtn.setBackground(new Color(59, 130, 246));
        profileBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        profileBtn.setFocusPainted(false);
        profileBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        profileBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                profileBtn.setBackground(new Color(37, 99, 235));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                profileBtn.setBackground(new Color(59, 130, 246));
            }
        });

        profileBtn.addActionListener(e -> {
            new Provider_account_c(me_client, applicant);
            frame.dispose();
        });

        buttonsPanel.add(approveBtn);
        buttonsPanel.add(removeBtn);
        buttonsPanel.add(profileBtn);

        cardPanel.add(buttonsPanel);

        mainPanel.add(cardPanel);
        frame.add(mainPanel);
        frame.setVisible(true);
    }
}
