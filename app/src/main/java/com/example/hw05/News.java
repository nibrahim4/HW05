package com.example.hw05;

public class News {

    public Source source;
    public String author;
    public String urlToImage;
    public String publishedAt;
    public String title;

    @Override
    public String toString() {
        return "News{" +
                "source=" + source +
                ", author='" + author + '\'' +
                ", urlToImage='" + urlToImage + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
