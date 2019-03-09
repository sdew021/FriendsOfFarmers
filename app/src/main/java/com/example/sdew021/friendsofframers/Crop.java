package com.example.sdew021.friendsofframers;

public class Crop {
    public int Crop_image;
    private String Stock;
    private String Name;
    private String Porders;

    public Crop(){}

    public Crop( String name, String stock, String porders) {
        this.Stock = stock;
        this.Name = name;
        this.Porders = porders;
    }


    public String getStock() {
        return Stock;
    }

    public String getName() {
        return Name;
    }

    public String getPorders() {
        return Porders;
    }
}
