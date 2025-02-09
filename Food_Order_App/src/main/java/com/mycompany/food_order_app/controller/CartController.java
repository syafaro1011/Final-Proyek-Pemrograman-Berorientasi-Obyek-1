package com.mycompany.food_order_app.controller;

import com.mycompany.food_order_app.db.AppQuery;
import com.mycompany.food_order_app.db.DatabaseConnection;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Alert;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CartController {

    private final AppQuery appQuery = new AppQuery();

    @FXML
    private GridPane grid;

    @FXML
    private Label labelTotalBayar;

    @FXML
    private ScrollPane scroll;

    @FXML
    private Button btnPesan;
    
    @FXML
    private Button btnX;

    @FXML
    public void initialize() {
        loadCartData();
        btnPesan.setOnAction(event -> handleOrder());
    }

    private void loadCartData() {
        try {
            ResultSet rs = appQuery.getCartData(); // Menggunakan metode getCartData dari AppQuery
            int row = 0;
            double totalBayar = 0;

            while (rs.next()) {
                String nama = rs.getString("nama");
                int jumlah = rs.getInt("jumlah");
                double subtotal = rs.getDouble("subtotal");

                // Membuat label untuk setiap kolom
                Label namaLabel = new Label(nama);
                Label jumlahLabel = new Label(String.valueOf(jumlah));
                Label subtotalLabel = new Label(String.format("%.2f", subtotal));

                // Menambahkan ke grid
                grid.addRow(row, namaLabel, jumlahLabel, subtotalLabel);

                totalBayar += subtotal; // Menambahkan subtotal ke total bayar
                row++;
            }

            labelTotalBayar.setText(String.format("Rp. %.2f", totalBayar));
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Gagal memuat data keranjang.");
        }
    }

    private void handleOrder() {
        try {
            int rowsDeleted = appQuery.clearCart(); // Menggunakan metode clearCart dari AppQuery
            if (rowsDeleted > 0) {
                grid.getChildren().clear();
                labelTotalBayar.setText("Total: 0.00");
                showAlert("Pesanan Berhasil", "Silahkan Bayar Ke Kasir!");
            } else {
                showAlert("Gagal", "Tidak ada data untuk dihapus.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Gagal menghapus data dari keranjang.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    @FXML
    private void handleMenu() {
        try {
            Stage stage = (Stage) Stage.getWindows().get(0);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/food_order_app/CustomerView.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

