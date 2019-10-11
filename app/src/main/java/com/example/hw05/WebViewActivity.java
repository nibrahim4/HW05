package com.example.hw05;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends AppCompatActivity {

    public Bundle extrasFromNews;
    public News selectedNews;
    public WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        setTitle("Web View Activity");

        extrasFromNews = getIntent().getExtras().getBundle("toWebView");
        selectedNews = (News) extrasFromNews.getSerializable("news");
        Log.d("demo", "onCreate: " + selectedNews.url);
        webView = findViewById(R.id.webView);
        webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        webView.loadUrl(selectedNews.url);

    }
}
