package crud;

import ui.CustomerForm;
import ui.AccountForm;
import ui.TransactionForm;

import javax.swing.*;

public class MainMenu {
    public static void main(String[] args) {
        String[] options = {"Customer Form", "Account Form", "Transaction Form"};
        int choice = JOptionPane.showOptionDialog(null, "Select Module", "Banking System",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        switch (choice) {
            case 0 -> new CustomerForm();
            case 1 -> new AccountForm();
            case 2 -> new TransactionForm();
        }
    }
}