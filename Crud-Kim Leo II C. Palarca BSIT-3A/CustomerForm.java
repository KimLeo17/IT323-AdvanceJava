package ui;

import dao.CustomerDAO;
import models.Customer;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;

public class CustomerForm extends JFrame {
    private JTextField txtFirstName, txtLastName, txtEmail;
    private JButton btnAdd, btnLoad;
    private JTextArea txtOutput;

    public CustomerForm() {
        setTitle("Customer Management");
        setSize(400, 400);
        setLayout(null);

        JLabel lblF = new JLabel("First Name:");
        lblF.setBounds(20, 20, 100, 25);
        add(lblF);
        txtFirstName = new JTextField();
        txtFirstName.setBounds(130, 20, 200, 25);
        add(txtFirstName);

        JLabel lblL = new JLabel("Last Name:");
        lblL.setBounds(20, 60, 100, 25);
        add(lblL);
        txtLastName = new JTextField();
        txtLastName.setBounds(130, 60, 200, 25);
        add(txtLastName);

        JLabel lblE = new JLabel("Email:");
        lblE.setBounds(20, 100, 100, 25);
        add(lblE);
        txtEmail = new JTextField();
        txtEmail.setBounds(130, 100, 200, 25);
        add(txtEmail);

        btnAdd = new JButton("Add Customer");
        btnAdd.setBounds(20, 140, 150, 30);
        add(btnAdd);

        btnLoad = new JButton("Load Customers");
        btnLoad.setBounds(180, 140, 150, 30);
        add(btnLoad);

        txtOutput = new JTextArea();
        txtOutput.setBounds(20, 180, 340, 160);
        add(txtOutput);

        JLabel lblInfo = new JLabel("Palarca, Kim Leo II C. - BSIT 3A");
        lblInfo.setBounds(60, 343, 262, 25);
        getContentPane().add(lblInfo);
        
        CustomerDAO dao = new CustomerDAO();

        btnAdd.addActionListener(e -> {
            try {
                Customer c = new Customer(0, txtFirstName.getText(), txtLastName.getText(), txtEmail.getText());
                dao.addCustomer(c);
                JOptionPane.showMessageDialog(this, "Customer added!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        btnLoad.addActionListener(e -> {
            try {
                List<Customer> list = dao.getAllCustomers();
                txtOutput.setText("");
                for (Customer c : list) {
                    txtOutput.append(c.getId() + ": " + c.getFirstName() + " " + c.getLastName() + " - " + c.getEmail() + "\n");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}