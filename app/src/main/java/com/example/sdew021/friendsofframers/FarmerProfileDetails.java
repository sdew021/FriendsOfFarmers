package com.example.sdew021.friendsofframers;

public class FarmerProfileDetails {
    String name,email,contact,currentAdd,permanentAdd;
    Upload image;
    int rating;

    public FarmerProfileDetails(String name, String email, String contact, String currentAdd, String permanentAdd, Upload image,int rating) {
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.currentAdd = currentAdd;
        this.permanentAdd = permanentAdd;
        this.image = image;
        this.rating=rating;

    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCurrentAdd() {
        return currentAdd;
    }

    public void setCurrentAdd(String currentAdd) {
        this.currentAdd = currentAdd;
    }

    public String getPermanentAdd() {
        return permanentAdd;
    }

    public void setPermanentAdd(String permanentAdd) {
        this.permanentAdd = permanentAdd;
    }

    public Upload getImage() {
        return image;
    }

    public void setImage(Upload image) {
        this.image = image;
    }

    public FarmerProfileDetails(){}
}
