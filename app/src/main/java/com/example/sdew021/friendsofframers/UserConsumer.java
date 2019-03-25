package com.example.sdew021.friendsofframers;

class UserConsumer {

    public String name,contact,email,currentAdd,permanentAdd,password;

    public UserConsumer(){

    }

    public UserConsumer(String name, String phone, String email, String currentAddress, String permanentAddress,String password) {
        this.name = name;
        this.contact = phone;
        this.email = email;
        this.currentAdd = currentAddress;
        this.permanentAdd = permanentAddress;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return contact;
    }

    public void setPhone(String phone) {
        this.contact = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCurrentAddress() {
        return currentAdd;
    }

    public void setCurrentAddress(String currentAddress) {
        this.currentAdd = currentAddress;
    }

    public String getPermanentAddress() {
        return permanentAdd;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAdd= permanentAddress;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", phone='" + contact + '\'' +
                ", email='" + email + '\'' +
                ", currentAddress='" + currentAdd + '\'' +
                ", permanentAddress='" + permanentAdd + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}