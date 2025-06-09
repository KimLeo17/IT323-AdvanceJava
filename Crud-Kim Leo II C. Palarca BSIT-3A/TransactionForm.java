package ui;

import dao.TransactionDAO;

import javax.swing.*;

public class TransactionForm extends JFrame {
    private JTextField txtAccountId, txtAmount;
    private JComboBox<String> cmbType;
    private JButton btnSubmit;

    public TransactionForm() {
        setTitle("Perform Transaction");
        setSize(350, 250);
        setLayout(null);

        JLabel lblId = new JLabel("Account ID:");
        lblId.setBounds(20, 20, 100, 25);
        add(lblId);
        txtAccountId = new JTextField();
        txtAccountId.setBounds(130, 20, 150, 25);
        add(txtAccountId);

        JLabel lblType = new JLabel("Type:");
        lblType.setBounds(20, 60, 100, 25);
        add(lblType);
        cmbType = new JComboBox<>(new String[]{"Deposit", "Withdraw"});
        cmbType.setBounds(130, 60, 150, 25);
        add(cmbType);

        JLabel lblAmt = new JLabel("Amount:");
        lblAmt.setBounds(20, 100, 100, 25);
        add(lblAmt);
        txtAmount = new JTextField();
        txtAmount.setBounds(130, 100, 150, 25);
        add(txtAmount);

        btnSubmit = new JButton("Execute");
        btnSubmit.setBounds(90, 150, 120, 30);
        add(btnSubmit);

        JLabel lblInfo = new JLabel("Palarca, Kim Leo II C. - BSIT 3A");
        lblInfo.setBounds(60, 343, 262, 25);
        getContentPane().add(lblInfo);
        
        TransactionDAO dao = new TransactionDAO();

        btnSubmit.addActionListener(e -> {
            try {
                int accId = Integer.parseInt(txtAccountId.getText());
                String type = (String) cmbType.getSelectedItem();
                double amt = Double.parseDouble(txtAmount.getText());

                dao.performTransaction(accId, type, amt);
                JOptionPane.showMessageDialog(this, "Transaction successful!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}