package com.example.resolve.MainUserDashBoard;

public class UserComplainHelperClass {
    String subject,category,time,message,status;
    public UserComplainHelperClass(){

    }
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserComplainHelperClass(String category, String message, String status, String subject, String time) {
        this.subject = subject;
        this.category = category;
        this.time = time;
        this.message = message;
        this.status = status;
    }
}
