package com.emirtopaloglu.unikolik.Model;

public class Comment {

    String username, text, date, post_id;

    public Comment(String username, String text, String date, String post_id) {
        this.username = username;
        this.text = text;
        this.date = date;
        this.post_id = post_id;
    }

    public String getUsername() {
        return username;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }

    public String getPostId() {
        return post_id;
    }
}
