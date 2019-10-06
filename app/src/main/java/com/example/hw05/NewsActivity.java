package com.example.hw05;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class NewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        setTitle(""); //TODO Set the title to the name of the source
    }
}
