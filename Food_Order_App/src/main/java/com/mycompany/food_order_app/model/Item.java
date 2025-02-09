package com.mycompany.food_order_app.model;


public abstract class Item {
    private int id;
    private String nama;
    private double harga;
    private boolean ketersediaan;
    private String path;

    public Item(int id, String nama, double harga, boolean ketersediaan, String path) {
        this.id = id;
        this.nama = nama;
        this.harga = harga;
        this.ketersediaan = ketersediaan;
        this.path = path;
    }
    
    public Item(String nama, double harga, boolean ketersediaan, String path) {
        this.nama = nama;
        this.harga = harga;
        this.ketersediaan = ketersediaan;
        this.path = path;
    }

    

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    
    public void setNama(String nama) {
        this.nama = nama;
    }

    
    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public boolean isKetersediaan() {
        return ketersediaan;
    }

    public void setKetersediaan(boolean ketersediaan) {
        this.ketersediaan = ketersediaan;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    // Metode abstrak 
    public abstract double hitungTotalHarga();
}

   
