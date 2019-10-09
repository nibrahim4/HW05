package com.example.hw05;

import java.io.Serializable;

public class News implements Serializable {

    public Source source;
    public String author;
    public String urlToImage;
    public String publishedAt;
    public String title;
    public String url;

    @Override
    public String toString() {
        return "News{" +
                "source=" + source +
                ", author='" + author + '\'' +
                ", urlToImage='" + urlToImage + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
