package com.example.hw05;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Source implements Serializable {

    public String id;
    public String name;

    @Override
    public String toString() {
        return "Source{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
