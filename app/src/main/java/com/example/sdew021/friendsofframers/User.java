/*
 *   Contributed by Rishi Sharma and Prateek
 *   17CO135 and 17CO130
 */


package com.example.sdew021.friendsofframers;



public class User {
    private String contact;
    private String Address;
    private String Name;
    private String Price;
    private String Quantity;

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public User(String name, String address, String contact, String price, String quantity) {
        this.contact = contact;
        Address = address;
        Name = name;
        Price = price;
        Quantity = quantity;
    }

    public String getContact() {
        return contact;
    }

    public String getAddress() {
        return Address;
    }

    public String getName() {
        return Name;
    }

    public String getPrice() {
        return Price;
    }

    public String getQuantity() {
        return Quantity;
    }

    User(){}
}