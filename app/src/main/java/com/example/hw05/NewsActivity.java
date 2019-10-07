package com.example.hw05;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ProgressBar;

public class NewsActivity extends AppCompatActivity {

    public Bundle extrasFromMain;
    public ProgressBar pb_loadingNews;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        pb_loadingNews = findViewById(R.id.pb_loadingNews);
        
        extrasFromMain = getIntent().getExtras().getBundle("toNews");
        Source selectedSource = (Source) extrasFromMain.getSerializable("sourceName");
        setTitle(selectedSource.name);
    }
}
