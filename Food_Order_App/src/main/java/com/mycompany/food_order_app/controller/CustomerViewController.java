package com.mycompany.food_order_app.controller;

import com.mycompany.food_order_app.controller.ItemCardController;
import com.mycompany.food_order_app.db.AppQuery;
import com.mycompany.food_order_app.model.foodDrink;
import java.io.IOException;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CustomerViewController implements Initializable {

    @FXML
    private VBox chosenFoodDrinkCard;

    @FXML
    private ImageView fooddrinkImg;

    @FXML
    private Label labelNama;

    @FXML
    private Label labelHarga;

    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scroll;
    
     @FXML
    private Button btnTologin;
     
     @FXML
    private Button btnAddCart;
     
     @FXML
    private Spinner<Integer> spinJumlah;
     
     @FXML
    private Button btnCart;
    
    @FXML
    private void handleLogin() {
        try {
            Stage stage = (Stage) Stage.getWindows().get(0);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/food_order_app/loginAdmin.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handCart() {
        try {
            Stage stage = (Stage) Stage.getWindows().get(0);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/food_order_app/Cart.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    @FXML
private void handleAddToCart() {
    try {
        
        String nama = labelNama.getText();
        double harga = Double.parseDouble(labelHarga.getText().replace("Rp ", "").replace(",", ""));
        int jumlah = (int) spinJumlah.getValue();
        double subtotal = harga * jumlah;

        // Panggil metode untuk menyimpan data ke tabel `cart`
        AppQuery query = new AppQuery();
        query.addToCart(nama, harga, jumlah, subtotal);

        showAlert("Sukses", "Item berhasil ditambahkan ke cart!", Alert.AlertType.INFORMATION);
    } catch (Exception e) {
        e.printStackTrace();
        showAlert("Error", "Gagal menambahkan item ke cart. Silakan coba lagi.", Alert.AlertType.ERROR);
    }
}


    private ObservableList<foodDrink> foodDrinkList; 
    private foodDrink selectedFoodDrink; 

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AppQuery query = new AppQuery();
        
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        spinJumlah.setValueFactory(valueFactory);
        
        
        foodDrinkList = query.getFooddrinkList(); 
        if (!foodDrinkList.isEmpty()) {
            setChosenFoodDrink(foodDrinkList.get(0)); 
        }
        populateGrid(); 
    }

    // Menampilkan detail item di chosenFoodDrinkCard
    private void setChosenFoodDrink(foodDrink foodDrink) {
        selectedFoodDrink = foodDrink;
        labelNama.setText(foodDrink.getNama());
        labelHarga.setText("Rp " + foodDrink.getHarga());
        Image image = new Image(foodDrink.getPath()); // Path gambar dari database
        fooddrinkImg.setImage(image);
    }
    
    

    // Mengisi GridPane dengan card (item)
    private void populateGrid() {
        int column = 0;
        int row = 1;

        try {
            for (foodDrink foodDrink : foodDrinkList) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/mycompany/food_order_app/ItemCard.fxml"));
                AnchorPane card = fxmlLoader.load();

                // Atur data pada card
                ItemCardController controller = fxmlLoader.getController();
                controller.setData(foodDrink, this::setChosenFoodDrink); // Klik card untuk memanggil setChosenFoodDrink

                if (column == 3) { // Maksimal 3 kolom per baris
                    column = 0;
                    row++;
                }

                grid.add(card, column++, row); // Tambahkan card ke GridPane
                // Sesuaikan ukuran card
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void showAlert(String title, String message, Alert.AlertType alertType) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setHeaderText(null); // Tidak perlu header
    alert.setContentText(message);
    alert.showAndWait();
    }
    
}
