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

import bibo.demo.Customer;
import bibo.demo.Item;
import bibo.demo.Order;

public class OrderRepository {
    private static final String URL = "jdbc:postgresql://localhost:5432/Online_Shopping_System";
    private static final String USER = "postgres";
    private static final String PASSWORD = "598041";

    private static final Logger logger = LoggerFactory.getLogger(OrderRepository.class);

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void addOrder(Order order) {
        String sql = "INSERT INTO orders (order_id, product_id, quantity, customer_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, order.getOrderId());
            stmt.setInt(2, order.getProduct().getProductId());
            stmt.setInt(3, order.getQuantity());
            stmt.setInt(4, order.getCustomer().getCustomerId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error while adding order with ID " + order.getOrderId() + ": ", e);
        }
    }

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT o.order_id, i.product_id, i.name AS product_name, i.price, o.quantity, " +
                     "c.customer_id, c.name AS customer_name, c.number AS customer_number " +
                     "FROM orders o " +
                     "JOIN items i ON o.product_id = i.product_id " +
                     "JOIN customers c ON o.customer_id = c.customer_id";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Item item = new Item(rs.getInt("product_id"), rs.getString("product_name"), rs.getDouble("price"));
                Customer customer = new Customer(rs.getInt("customer_id"), rs.getString("customer_name"), rs.getLong("customer_number"));
                orders.add(new Order(rs.getInt("order_id"), item, rs.getInt("quantity"), customer));
            }
        } catch (SQLException e) {
            logger.error("Error while retrieving all orders: ", e);
        }
        return orders;
    }

    public Order getOrderById(int orderId) {
        String sql = "SELECT o.order_id, i.product_id, i.name AS product_name, i.price, o.quantity, " +
                     "c.customer_id, c.name AS customer_name, c.number AS customer_number " +
                     "FROM orders o " +
                     "JOIN items i ON o.product_id = i.product_id " +
                     "JOIN customers c ON o.customer_id = c.customer_id " +
                     "WHERE o.order_id = ?";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Item item = new Item(rs.getInt("product_id"), rs.getString("product_name"), rs.getDouble("price"));
                    Customer customer = new Customer(rs.getInt("customer_id"), rs.getString("customer_name"), rs.getLong("customer_number"));
                    return new Order(orderId, item, rs.getInt("quantity"), customer);
                }
            }
        } catch (SQLException e) {
            logger.error("Error while retrieving order with ID " + orderId + ": ", e);
        }
        return null;
    }

    public void deleteOrder(int orderId) {
        String sql = "DELETE FROM orders WHERE order_id = ?";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error while deleting order with ID " + orderId + ": ", e);
        }
    }
}
