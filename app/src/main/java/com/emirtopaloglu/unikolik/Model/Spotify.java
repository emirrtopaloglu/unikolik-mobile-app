package com.emirtopaloglu.unikolik.Model;

public class Spotify {
    String image, title, creator, uri;

    public Spotify(String image, String title, String creator, String uri) {
        this.image = image;
        this.title = title;
        this.creator = creator;
        this.uri = uri;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getCreator() {
        return creator;
    }

    public String getUri() {
        return uri;
    }
}
