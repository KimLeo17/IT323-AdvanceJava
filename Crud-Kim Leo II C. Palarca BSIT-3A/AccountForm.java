package ui;

import dao.AccountDAO;
import models.Account;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;

public class AccountForm extends JFrame {
    private JTextField txtCustomerId, txtType, txtBalance;
    private JButton btnAdd, btnLoad;
    private JTextArea txtOutput;

    public AccountForm() {
        setTitle("Account Management");
        setSize(400, 400);
        getContentPane().setLayout(null);

        JLabel lblC = new JLabel("Customer ID:");
        lblC.setBounds(20, 20, 100, 25);
        getContentPane().add(lblC);
        txtCustomerId = new JTextField();
        txtCustomerId.setBounds(130, 20, 200, 25);
        getContentPane().add(txtCustomerId);

        JLabel lblT = new JLabel("Account Type:");
        lblT.setBounds(20, 60, 100, 25);
        getContentPane().add(lblT);
        txtType = new JTextField();
        txtType.setBounds(130, 60, 200, 25);
        getContentPane().add(txtType);

        JLabel lblB = new JLabel("Balance:");
        lblB.setBounds(20, 100, 100, 25);
        getContentPane().add(lblB);
        txtBalance = new JTextField();
        txtBalance.setBounds(130, 100, 200, 25);
        getContentPane().add(txtBalance);

        btnAdd = new JButton("Add Account");
        btnAdd.setBounds(20, 140, 150, 30);
        getContentPane().add(btnAdd);

        btnLoad = new JButton("Load Accounts");
        btnLoad.setBounds(180, 140, 150, 30);
        getContentPane().add(btnLoad);

        txtOutput = new JTextArea();
        txtOutput.setBounds(20, 180, 340, 160);
        getContentPane().add(txtOutput);

        JLabel lblInfo = new JLabel("Palarca, Kim Leo II C. - BSIT 3A");
        lblInfo.setBounds(60, 343, 262, 25);
        getContentPane().add(lblInfo);
        
        AccountDAO dao = new AccountDAO();

        btnAdd.addActionListener(e -> {
            try {
                Account a = new Account(0, Integer.parseInt(txtCustomerId.getText()),
                        txtType.getText(), Double.parseDouble(txtBalance.getText()));
                dao.addAccount(a);
                JOptionPane.showMessageDialog(this, "Account added!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        btnLoad.addActionListener(e -> {
            try {
                List<Account> list = dao.getAllAccounts();
                txtOutput.setText("");
                for (Account a : list) {
                    txtOutput.append("Account ID: " + a.getId() + " | CustID: " + a.getCustomerId()
                            + " | Type: " + a.getAccountType() + " | Bal: $" + a.getBalance() + "\n");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}