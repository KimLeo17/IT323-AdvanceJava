package dao;

import crud_db.DBConnection;

import java.sql.*;

public class TransactionDAO {

    public void performTransaction(int accountId, String type, double amount) throws SQLException {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            String updateBalance = type.equals("Deposit") ?
                    "UPDATE accounts SET balance = balance + ? WHERE account_id = ?" :
                    "UPDATE accounts SET balance = balance - ? WHERE account_id = ?";

            try (PreparedStatement ps1 = conn.prepareStatement(updateBalance)) {
                ps1.setDouble(1, amount);
                ps1.setInt(2, accountId);
                ps1.executeUpdate();
            }

            String insertTransaction = "INSERT INTO transactions (account_id, transaction_type, amount) VALUES (?, ?, ?)";
            try (PreparedStatement ps2 = conn.prepareStatement(insertTransaction)) {
                ps2.setInt(1, accountId);
                ps2.setString(2, type);
                ps2.setDouble(3, amount);
                ps2.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (conn != null) conn.close();
        }
    }
}