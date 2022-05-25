package com.emirtopaloglu.unikolik.Model;

public class Post {

    String date, imageUrl, text, user, username, id;

    public Post(String date, String imageUrl, String text, String user, String username, String id) {
        this.date = date;
        this.imageUrl = imageUrl;
        this.text = text;
        this.user = user;
        this.username = username;
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getText() {
        return text;
    }

    public String getUser() {
        return user;
    }

    public String getUsername() {
        return username;
    }

    public String getId() {
        return id;
    }
}
