package bibo.demo.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bibo.demo.Customer;
import bibo.demo.Item;
import bibo.demo.Order;

public class OrderRepository {
    private static final String URL = "jdbc:postgresql://localhost:5432/Online_Shopping_System";
    private static final String USER = "postgres";
    private static final String PASSWORD = "598041";

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
}
public void addOrder(Order order) {
    String sql = "INSERT INTO orders (order_id, product_id, quantity, customer_id) VALUES (?, ?, ?, ?)";
    try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, order.getOrderId());
        stmt.setInt(2, order.getProduct().getProductId());
        stmt.setInt(3, order.getQuantity());
        stmt.setInt(4, order.getCustomer().getCustomerId());
        stmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
public List<Order> getAllOrders() {
    List<Order> orders = new ArrayList<>();
    String sql = "SELECT o.order_id, i.product_id, i.name AS product_name, i.price, o.quantity, c.customer_id, c.name AS customer_name, c.number FROM orders o JOIN items i ON o.product_id = i.product_id JOIN customers c ON o.customer_id = c.customer_id";
    try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
        while (rs.next()) {
            int orderId = rs.getInt("order_id");
            int productId = rs.getInt("product_id");
            String productName = rs.getString("product_name");
            double price = rs.getDouble("price");
            int quantity = rs.getInt("quantity");
            int customerId = rs.getInt("customer_id");
            String customerName = rs.getString("customer_name");
            long customerNumber = rs.getLong("number");

            Item item = new Item(productId, productName, price);
            Customer customer = new Customer(customerId, customerName, customerNumber);
            orders.add(new Order(orderId, item, quantity, customer));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return orders;
}

public void deleteOrder(int orderId) {
    String sql = "DELETE FROM orders WHERE order_id = ?";
    try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, orderId);
        stmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
}