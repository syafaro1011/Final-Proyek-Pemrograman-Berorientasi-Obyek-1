/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.food_order_app.controller;

import com.mycompany.food_order_app.model.foodDrink;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.function.Consumer;

public class ItemCardController {

    @FXML
    private Label cardLabelNama;

    @FXML
    private Label cardLabelHarga;

    @FXML
    private ImageView cardImg;

    private foodDrink foodDrink;

    @FXML
    public void setData(foodDrink foodDrink, Consumer<foodDrink> onCardClick) {
        this.foodDrink = foodDrink;
        cardLabelNama.setText(foodDrink.getNama());
        cardLabelHarga.setText("Rp " + foodDrink.getHarga());
        cardImg.setImage(new Image(foodDrink.getPath()));

        // Atur klik pada card
        cardImg.setOnMouseClicked((MouseEvent event) -> onCardClick.accept(foodDrink));
    }
}

