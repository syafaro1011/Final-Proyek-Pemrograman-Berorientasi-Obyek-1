/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.food_order_app.model;


public class foodDrink extends Item {
    private int jumlah;
    private double totalHarga;

    public foodDrink(String nama, double harga, boolean ketersediaan, String path) {
        super(nama, harga, ketersediaan, path);
        this.jumlah = 1;
        this.totalHarga = harga; 
    }
    
    public foodDrink(Integer id, String nama, double harga, boolean ketersediaan, String path) {
        super(id, nama, harga, ketersediaan, path);
        this.jumlah = 1;
        this.totalHarga = harga; 
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
        this.totalHarga = jumlah * getHarga();
    }

    public double getTotalHarga() {
        return totalHarga;
    }

    @Override
    public double hitungTotalHarga() {
        return jumlah * getHarga();  
    }
}
