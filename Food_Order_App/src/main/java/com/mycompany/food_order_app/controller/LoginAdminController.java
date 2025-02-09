package com.mycompany.food_order_app.controller;


import com.mycompany.food_order_app.db.DatabaseConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginAdminController {
    
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorMessage;

    
    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (authenticateAdmin(username, password)) {
            try {
                Stage stage = (Stage) usernameField.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/food_order_app/ManageFood.fxml"));
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
                errorMessage.setText("Gagal membuka halaman Dashboard!");
            }
        } else {
            // Login gagal
            errorMessage.setText("Username atau password salah!");
        }
    }
    
    @FXML
    private void handleKembali() {
        try {
            Stage stage = (Stage) Stage.getWindows().get(0);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/food_order_app/CustomerView.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    private boolean authenticateAdmin(String username, String password) {
        String query = "SELECT * FROM admin WHERE username = ? AND password = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next(); // True jika ada data yang cocok

        } catch (SQLException e) {
            e.printStackTrace();
            errorMessage.setText("Error saat mengakses database!");
            return false;
        }
    }
}
