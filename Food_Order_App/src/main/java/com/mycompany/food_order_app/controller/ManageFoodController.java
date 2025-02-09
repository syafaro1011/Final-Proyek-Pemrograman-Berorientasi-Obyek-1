package com.mycompany.food_order_app.controller;

import javafx.scene.image.ImageView;
import com.mycompany.food_order_app.db.AppQuery;
import com.mycompany.food_order_app.model.foodDrink;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class ManageFoodController implements Initializable{
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        showFooddrink();
        tableItem.setOnMouseClicked(event -> handleTableClick());
    }
    
    private int selectedItemID;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnHapus;

    @FXML
    private Button btnSelesai;

    @FXML
    private Button btnTambah;

    @FXML
    private Button btnUpdate;

    @FXML
    private CheckBox checkItem;

    @FXML
    private TextField fieldHarga;

    @FXML
    private TextField fieldNama;

    @FXML
    private TextField fieldPath;

    @FXML
    private TableView tableItem;
    
    @FXML
    private TableColumn<foodDrink, Integer> colID;
    
    @FXML
    private TableColumn<foodDrink, String> colNAMA;
    
    @FXML
    private TableColumn<foodDrink, Double> colHARGA;
    
    @FXML
    private TableColumn<foodDrink, Boolean> colKETERSEDIAAN;
    
    @FXML
    private TableColumn<foodDrink, String> colPATH;
    
    @FXML
    private ImageView refresh;
    
    @FXML
    private void addItem() {
        double harga = Double.parseDouble(fieldHarga.getText());
        com.mycompany.food_order_app.model.foodDrink fooddrink = new com.mycompany.food_order_app.model.foodDrink
        (fieldNama.getText(), harga, checkItem.isSelected(), fieldPath.getText());
        
        com.mycompany.food_order_app.db.AppQuery query = new AppQuery();
        query.addItem(fooddrink);
        
        clearInputFields();
    }
    
    @FXML
    private void showFooddrink() {
        com.mycompany.food_order_app.db.AppQuery query = new AppQuery();
        ObservableList<foodDrink> list = query.getFooddrinkList();
        colID.setCellValueFactory(new PropertyValueFactory<foodDrink, Integer>("id"));
        colNAMA.setCellValueFactory(new PropertyValueFactory<foodDrink, String>("nama"));
        colHARGA.setCellValueFactory(new PropertyValueFactory<foodDrink, Double>("harga"));
        colKETERSEDIAAN.setCellValueFactory(new PropertyValueFactory<foodDrink, Boolean>("ketersediaan"));
        colPATH.setCellValueFactory(new PropertyValueFactory<foodDrink, String>("path"));
        tableItem.setItems(list);
    }
    
    @FXML
private void handleTableClick() {
    com.mycompany.food_order_app.model.foodDrink selectedItem = (com.mycompany.food_order_app.model.foodDrink) tableItem.getSelectionModel().getSelectedItem();
    if (selectedItem != null) {
        
        selectedItemID = selectedItem.getId();
        fieldNama.setText(selectedItem.getNama());
        fieldHarga.setText(String.valueOf(selectedItem.getHarga()));
        checkItem.setSelected(selectedItem.isKetersediaan());
        fieldPath.setText(selectedItem.getPath());
    }
}

@FXML
private void updateItem() {
    if (selectedItemID != 0) {
        try {
            // Ambil nilai dari TextField
            String nama = fieldNama.getText();
            double harga = Double.parseDouble(fieldHarga.getText());
            boolean ketersediaan = checkItem.isSelected();
            String path = fieldPath.getText();

            // Update data di database
            com.mycompany.food_order_app.db.AppQuery query = new com.mycompany.food_order_app.db.AppQuery();
            query.updateItem(selectedItemID, nama, harga, ketersediaan, path);

            // Refresh tabel
            showFooddrink();

            // Kosongkan input field setelah update
            clearInputFields();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

@FXML
private void refreshTable() {
    showFooddrink(); // Panggil metode untuk memuat ulang data tabel
    clearInputFields(); // Bersihkan input field
}


private void clearInputFields() {
    fieldNama.clear();
    fieldHarga.clear();
    checkItem.setSelected(false);
    fieldPath.clear();
    selectedItemID = 0; // Reset ID
}


    @FXML
    private void handleCustomer() {
        try {
            Stage stage = (Stage) Stage.getWindows().get(0);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/food_order_app/CustomerView.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
@FXML
private void deleteItem() {
    // Periksa apakah ada item yang dipilih
    com.mycompany.food_order_app.model.foodDrink selectedFoodDrink = 
        (com.mycompany.food_order_app.model.foodDrink) tableItem.getSelectionModel().getSelectedItem();
    
    if (selectedFoodDrink == null) {
        // Jika tidak ada yang dipilih, tampilkan peringatan
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Peringatan");
        alert.setHeaderText("Tidak ada item yang dipilih!");
        alert.setContentText("Silakan pilih item di tabel untuk dihapus.");
        alert.showAndWait();
    } else {
        // Konfirmasi sebelum menghapus
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Konfirmasi Hapus");
        confirmation.setHeaderText("Anda yakin ingin menghapus item ini?");
        confirmation.setContentText("Item: " + selectedFoodDrink.getNama());

        // Jika pengguna mengonfirmasi
        if (confirmation.showAndWait().get() == ButtonType.OK) {
            AppQuery query = new AppQuery();
            query.deleteItem(selectedFoodDrink.getId()); // Panggil metode delete dari AppQuery
            refreshTable(); // Refresh tabel setelah penghapusan
        }
    }
}

    
}


