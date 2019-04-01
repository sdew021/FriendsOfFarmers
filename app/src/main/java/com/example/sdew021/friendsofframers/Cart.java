
/*
 *   Contributed by Rahul Kumar
 *   17CO133
 */


package com.example.sdew021.friendsofframers;

public class Cart {
    private String name;
    private String quantity;
    private String price;
    private String image;

    Cart() {

    }

    public Cart(String name, String quantity, String price, String image) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.image = image;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "name='" + name + '\'' +
                ", quantity='" + quantity + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}










