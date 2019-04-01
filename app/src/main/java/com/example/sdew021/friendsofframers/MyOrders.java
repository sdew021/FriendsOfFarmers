/*
 *   Contributed by Rahul Kumar
 *   17CO133
 */


package com.example.sdew021.friendsofframers;

public class MyOrders {
    private String cropname;
    private String quantity;
    private String cropPrice;
    private String shippingAddress;

    public MyOrders(String cropname, String quantity, String cropPrice, String shippingAddress) {
        this.cropname = cropname;
        this.quantity = quantity;
        this.cropPrice = cropPrice;
        this.shippingAddress = shippingAddress;
    }

    public MyOrders() {
    }

    public String getCropname() {
        return cropname;
    }

    public void setCropname(String cropname) {
        this.cropname = cropname;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCropPrice() {
        return cropPrice;
    }

    public void setCropPrice(String cropPrice) {
        this.cropPrice = cropPrice;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    @Override
    public String toString() {
        return "MyOrders{" +
                "cropname='" + cropname + '\'' +
                ", quantity='" + quantity + '\'' +
                ", cropPrice='" + cropPrice + '\'' +
                ", shippingAddress='" + shippingAddress + '\'' +
                '}';
    }
}










