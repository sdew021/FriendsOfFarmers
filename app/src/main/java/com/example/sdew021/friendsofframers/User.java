package com.example.sdew021.friendsofframers;

public class User {
    private String contact;
    private String Address;
    private String Name;
    private String Price;
    private String delivery;
    private String Quantity;

    public User(String name,  String address,String contact, String price, String delivery, String quantity) {
        this.contact = contact;
        Address = address;
        Name = name;
        Price = price;
        this.delivery = delivery;
        Quantity = quantity;
    }

    public String getContact() {
        return "contact:"+contact;
    }

    public String getAddress() {
        return "Address:"+Address;
    }

    public String getName() {
        return Name;
    }

    public String getPrice() {
        return "Price:"+Price;
    }

    public String getDelivery() {
        return "Estimated delivery Time:"+delivery;
    }

    public String getQuantity() {
        return "Quantity:"+Quantity;
    }
}
