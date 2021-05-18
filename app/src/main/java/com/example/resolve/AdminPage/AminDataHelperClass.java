package com.example.resolve.AdminPage;

public class AminDataHelperClass {
    String name,email,position;
    public AminDataHelperClass(){}
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public AminDataHelperClass(String name, String email, String position) {
        this.name = name;
        this.email = email;
        this.position = position;
    }
}
