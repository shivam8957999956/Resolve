package com.example.resolve;

public class AnnouncementHelperClass {
String email,title,text;

    public AnnouncementHelperClass(){}
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public AnnouncementHelperClass(String email, String title, String text) {
        this.email = email;
        this.title = title;
        this.text = text;
    }
}
