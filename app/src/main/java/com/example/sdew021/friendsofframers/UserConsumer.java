package com.example.sdew021.friendsofframers;

class UserConsumer {

    public String name,phone,email,currentAddress,permanentAddress,password;

    public UserConsumer(){

    }

    public UserConsumer(String name, String phone, String email, String currentAddress, String permanentAddress,String password) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.currentAddress = currentAddress;
        this.permanentAddress = permanentAddress;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", currentAddress='" + currentAddress + '\'' +
                ", permanentAddress='" + permanentAddress + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}