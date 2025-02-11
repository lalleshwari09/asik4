package bibo.demo.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import bibo.demo.Customer;

@Repository
public class CustomerRepository {
    private static final Logger logger = LoggerFactory.getLogger(CustomerRepository.class);
    private static final String URL = "jdbc:postgresql://localhost:5432/Online_Shopping_System";
    private static final String USER = "postgres";
    private static final String PASSWORD = "598041";

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void addCustomer(Customer customer) {
        String sql = "INSERT INTO customers (customer_id, name, number) VALUES (?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customer.getCustomerId());
            stmt.setString(2, customer.getName());
            stmt.setLong(3, customer.getNumber());
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error adding customer with ID " + customer.getCustomerId() + ": ", e);
        }
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers";
        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                customers.add(new Customer(rs.getInt("customer_id"), rs.getString("name"), rs.getLong("number")));
            }
        } catch (SQLException e) {
            logger.error("Error retrieving all customers: ", e);
        }
        return customers;
    }

    public Customer getCustomer(int customerId) {
        String sql = "SELECT * FROM customers WHERE customer_id = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Customer(customerId, rs.getString("name"), rs.getLong("number"));
                }
            }
        } catch (SQLException e) {
            logger.error("Error retrieving customer with ID " + customerId + ": ", e);
        }
        return null;
    }

    public void deleteCustomer(int customerId) {
        String sql = "DELETE FROM customers WHERE customer_id = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error deleting customer with ID " + customerId + ": ", e);
        }
    }

    public void updateCustomer(int customerId, Customer updatedCustomer) {
        String sql = "UPDATE customers SET name = ?, number = ? WHERE customer_id = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, updatedCustomer.getName());
            stmt.setLong(2, updatedCustomer.getNumber());
            stmt.setInt(3, customerId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error updating customer with ID " + customerId + ": ", e);
        }
    }
}
