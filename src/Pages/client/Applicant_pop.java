package Pages.client;

import pack.Client;
import pack.Service;
import pack.ServiceProvider;

import javax.swing.*;

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
        JFrame frame = new JFrame("Applicant");
        frame.setSize(400, 250);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);


        // User name
        JLabel userNameLabel = new JLabel("User name :");
        userNameLabel.setBounds(30, 35, 100, 25);
        JLabel userNameField = new JLabel(applicant.getUser_name());
        userNameField.setBounds(150, 35, 300, 25);
        frame.add(userNameLabel);
        frame.add(userNameField);



        // Approve Button
        JButton approveBtn = new JButton("Approve");
        approveBtn.setBounds(30, 100, 100, 30);
        frame.add(approveBtn);

        approveBtn.addActionListener(e -> {
            int status = me_client.chooseProvider(applicant,service );
            if (status == 1) {
                JOptionPane.showMessageDialog(frame, "The Provider has been chosen for the Job!", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                new Service_page_c(me_client, service);
            }else if (status == 2) {
                JOptionPane.showMessageDialog(frame, "He is busy right now", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            else if(status == 3) {
                JOptionPane.showMessageDialog(frame, "You dont have enough money !", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            else if(status == 4) {
                JOptionPane.showMessageDialog(frame, "The Service's status is on-going !", "ERROR", JOptionPane.ERROR_MESSAGE);
            }

            frame.dispose();
        });

        // remove Button
        JButton removeBtn = new JButton("Remove");
        removeBtn.setBounds(150, 100, 100, 30);
        frame.add(removeBtn);

        removeBtn.addActionListener(e -> {
            me_client.deleteApplicant(service, applicant);
            JOptionPane.showMessageDialog(frame, "The Provider has been removed from the applicants!", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
            new Service_page_c(me_client, service);
            frame.dispose();
        });

        // Profile Button
        JButton profileBtn = new JButton("Profile");
        profileBtn.setBounds(270, 100, 100, 30);
        frame.add(profileBtn);

        profileBtn.addActionListener(e -> {
            new Provider_account_c(me_client, applicant);
            frame.dispose();
        });

        frame.setVisible(true);
    }
}
