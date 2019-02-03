package com.example.sdew021.friendsofframers;

public class Crop {
    private int Crop_image;
    private String Stock;
    private String Name;
    private String Porders;

    public Crop(int crop_image, String name, String stock, String porders) {
        this.Crop_image = crop_image;
        this.Stock = stock;
        this.Name = name;
        this.Porders = porders;
    }

    public int getCrop_image() {
        return Crop_image;
    }

    public String getStock() {
        return "Stock Avaialble:"+Stock;
    }

    public String getName() {
        return Name;
    }

    public String getPorders() {
        return "Pending Orders:"+Porders;
    }
}
