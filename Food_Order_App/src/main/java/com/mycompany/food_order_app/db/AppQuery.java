/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.food_order_app.db;

import com.mycompany.food_order_app.model.foodDrink;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author MyBook Hype AMD
 */
public class AppQuery {
    private final DatabaseConnection con = new DatabaseConnection();
    
    public void addItem(com.mycompany.food_order_app.model.foodDrink fooddrink) {
        try {
            DatabaseConnection.getConnection();
            java.sql.PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("insert into items(nama,harga,ketersediaan,path)values(?,?,?,?)");
            ps.setString(1, fooddrink.getNama());
            ps.setDouble(2, fooddrink.getHarga());
            ps.setBoolean(3, fooddrink.isKetersediaan());
            ps.setString(4, fooddrink.getPath());
            ps.execute();
            ps.close();
            DatabaseConnection.closeConnection();
        } catch (Exception e) {
        }
        
    }
    
    
    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public void updateItem(int id, String nama, double harga, boolean ketersediaan, String path) {
    try {
        DatabaseConnection.getConnection();
        java.sql.PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(
            "UPDATE items SET nama = ?, harga = ?, ketersediaan = ?, path = ? WHERE id = ?"
        );
        ps.setString(1, nama);
        ps.setDouble(2, harga);
        ps.setBoolean(3, ketersediaan);
        ps.setString(4, path);
        ps.setInt(5, id);
        ps.executeUpdate();
        ps.close();
        DatabaseConnection.closeConnection();
    } catch (Exception e) {
        e.printStackTrace();
    }
    
    }
    
    public void deleteItem(int id) {
    try {
        DatabaseConnection.getConnection();
        java.sql.PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("DELETE FROM items WHERE id = ?");
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
        DatabaseConnection.closeConnection();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    public ObservableList<foodDrink> getFooddrinkList() {
        ObservableList<foodDrink> foodDrinkList = FXCollections.observableArrayList();
        try {
            String query = "SELECT * FROM items";
            DatabaseConnection.getConnection();
            Statement st = DatabaseConnection.getConnection().createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                foodDrink s = new foodDrink(
                    rs.getInt("id"),
                    rs.getString("nama"),
                    rs.getDouble("harga"),
                    rs.getBoolean("ketersediaan"),
                    rs.getString("path")
                );
                foodDrinkList.add(s); // Tambahkan item ke dalam list
            }
            rs.close();
            st.close();
            DatabaseConnection.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return foodDrinkList;
    }
    
    public void addToCart(String nama, double harga, int jumlah, double subtotal) {
    String query = "INSERT INTO cart (nama, harga, jumlah, subtotal) VALUES (?, ?, ?, ?)";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {

        preparedStatement.setString(1, nama);
        preparedStatement.setDouble(2, harga);
        preparedStatement.setInt(3, jumlah);
        preparedStatement.setDouble(4, subtotal);

        preparedStatement.executeUpdate();
        System.out.println("Data berhasil ditambahkan ke tabel cart.");
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Gagal menambahkan data ke tabel cart.");
    }
}
    
    public ResultSet getCartData() throws SQLException {
    String query = "SELECT nama, jumlah, subtotal FROM cart";
    Connection connection = DatabaseConnection.getConnection();
    PreparedStatement stmt = connection.prepareStatement(query);
    return stmt.executeQuery();
}

    public int clearCart() throws SQLException {
    String query = "DELETE FROM cart";
    Connection connection = DatabaseConnection.getConnection();
    PreparedStatement stmt = connection.prepareStatement(query);
    int rowsDeleted = stmt.executeUpdate();
    stmt.close();
    connection.close();
    return rowsDeleted;
}



}
