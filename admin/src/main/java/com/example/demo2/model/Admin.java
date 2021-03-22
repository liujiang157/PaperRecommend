package com.example.demo2.model;

public class Admin {
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Admin(String name, String password) {
        this.name = name;
        this.password = password;
    }

    private String name;
    private String password;

}
