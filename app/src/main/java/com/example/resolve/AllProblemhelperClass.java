package com.example.resolve;

public class AllProblemhelperClass {

    String email,message,name,status,subject,time;
    public AllProblemhelperClass(){

    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public AllProblemhelperClass(String email, String message, String name, String status, String subject, String time) {
        this.email = email;
        this.message = message;
        this.name = name;
        this.status = status;
        this.subject = subject;
        this.time = time;
    }
}
