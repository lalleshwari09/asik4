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

import bibo.demo.Item;

@Repository
public class ItemRepository {
    
    private static final String URL = "jdbc:postgresql://localhost:5432/Online_Shopping_System";
    private static final String USER = "postgres";
    private static final String PASSWORD = "598041";
    
    private static final Logger logger = LoggerFactory.getLogger(ItemRepository.class);

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void addItem(Item item) {
        String sql = "INSERT INTO items (product_id, name, price) VALUES (?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, item.getProductId());
            stmt.setString(2, item.getName());
            stmt.setDouble(3, item.getPrice());
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error while adding item: ", e);
        }
    }

    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM items";
        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int productId = rs.getInt("product_id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                items.add(new Item(productId, name, price));
            }
        } catch (SQLException e) {
            logger.error("Error while fetching all items: ", e);
        }
        return items;
    }

    public void deleteItem(int productId) {
        String sql = "DELETE FROM items WHERE product_id = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error while deleting item with product_id " + productId + ": ", e);
        }
    }

    public void updateItem(int productId, Item updatedItem) {
        String sql = "UPDATE items SET name = ?, price = ? WHERE product_id = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, updatedItem.getName());
            stmt.setDouble(2, updatedItem.getPrice());
            stmt.setInt(3, productId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error while updating item with product_id " + productId + ": ", e);
        }
    }

    public Item getItem(int id) {
        String query = "SELECT * FROM items WHERE product_id = ?";
        try (Connection connection = connect(); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Item(
                            resultSet.getInt("product_id"),
                            resultSet.getString("name"),
                            resultSet.getDouble("price")
                    );
                }
            }
        } catch (SQLException e) {
            logger.error("Error while fetching item with product_id " + id + ": ", e);
        }
        return null;
    }
}
