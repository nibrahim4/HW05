package com.example.hw05;

import java.io.Serializable;

public class Source implements Serializable {

    public int id;
    public String name;

    @Override
    public String toString() {
        return "Source{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
