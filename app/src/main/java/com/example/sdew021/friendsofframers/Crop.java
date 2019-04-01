/*
 *   Contributed by Prateek Sahu
 *   17CO130
 */



package com.example.sdew021.friendsofframers;

public class Crop {
    public int Crop_image;
    private String stock;
    private String name;
    private String pendingOrders;
    private String price;


    public void setStock(String stock) {
        this.stock = stock;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPendingOrders(String pendingOrders) {
        this.pendingOrders = pendingOrders;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String  price) {
        this.price = price;
    }

    public Crop(){}

    public Crop( String name, String stock, String pendingOrders) {
        this.stock = stock;
        this.name = name;
        this.pendingOrders = pendingOrders;
    }


    public String getStock() {
        return stock;
    }

    public String getName() {
        return name;
    }

    public String getPendingOrders() {
        return pendingOrders;
    }
}
