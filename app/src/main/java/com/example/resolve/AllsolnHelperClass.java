package com.example.resolve;

public class AllsolnHelperClass {

    String name,email,solution,time;
    public AllsolnHelperClass(){

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

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public AllsolnHelperClass(String name, String email, String solution, String time) {
        this.name = name;
        this.email = email;
        this.solution = solution;
        this.time = time;
    }
}
