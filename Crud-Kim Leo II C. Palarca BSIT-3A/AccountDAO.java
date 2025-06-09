package dao;

import crud_db.DBConnection;
import models.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    public void addAccount(Account a) throws SQLException {
        String sql = "INSERT INTO accounts (customer_id, account_type, balance) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, a.getCustomerId());
            ps.setString(2, a.getAccountType());
            ps.setDouble(3, a.getBalance());
            ps.executeUpdate();
        }
    }

    public List<Account> getAllAccounts() throws SQLException {
        List<Account> list = new ArrayList<>();
        String sql = "SELECT * FROM accounts";
        try (Connection conn = DBConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Account(rs.getInt("account_id"), rs.getInt("customer_id"),
                        rs.getString("account_type"), rs.getDouble("balance")));
            }
        }
        return list;
    }

    public void updateAccount(Account a) throws SQLException {
        String sql = "UPDATE accounts SET account_type=?, balance=? WHERE account_id=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, a.getAccountType());
            ps.setDouble(2, a.getBalance());
            ps.setInt(3, a.getId());
            ps.executeUpdate();
        }
    }

    public void deleteAccount(int id) throws SQLException {
        String sql = "DELETE FROM accounts WHERE account_id=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}