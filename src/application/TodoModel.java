package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TodoModel {
    private ObservableList<TodoItem> daftarKegiatan;

    public TodoModel() {
        this.daftarKegiatan = FXCollections.observableArrayList();
        loadDataFromDatabase();
    }

    public ObservableList<TodoItem> getDaftarKegiatan() {
        return daftarKegiatan;
    }

    private void loadDataFromDatabase() {
        daftarKegiatan.clear();
        String query = "SELECT id, title, status FROM tasks ORDER BY created_at DESC";
        
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                daftarKegiatan.add(new TodoItem(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void tambahKegiatan(String kegiatan) {
        String query = "INSERT INTO tasks (title, status) VALUES (?, 'todo')";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, kegiatan);
            stmt.executeUpdate();
            loadDataFromDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void hapusKegiatan(TodoItem item) {
        String query = "DELETE FROM tasks WHERE id = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, item.getId());
            stmt.executeUpdate();
            loadDataFromDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method BARU untuk mengubah status
    public void updateStatus(TodoItem item, String newStatus) {
        String query = "UPDATE tasks SET status = ? WHERE id = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newStatus);
            stmt.setInt(2, item.getId());
            stmt.executeUpdate();
            
            // Update objek di memori agar UI langsung berubah
            item.setStatus(newStatus);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}