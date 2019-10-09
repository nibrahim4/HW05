package com.example.hw05;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity {

    public Bundle extrasFromMain;
    public ProgressBar pb_loadingNews;
    public Source selectedSource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        pb_loadingNews = findViewById(R.id.pb_loadingNews);
        pb_loadingNews.setVisibility(View.VISIBLE);
        extrasFromMain = getIntent().getExtras().getBundle("toNews");
        selectedSource = (Source) extrasFromMain.getSerializable("sourceName");
        setTitle(selectedSource.name);


        new GetHeadlinesAsync().execute("https://newsapi.org/v2/top-headlines?sources=" + selectedSource.id + "&apiKey=2cc75c20cb4e4b72b089efd93f3cc33a");
    }

    private class GetHeadlinesAsync extends AsyncTask<String, Void, ArrayList<News>> {

        @Override
        protected ArrayList<News> doInBackground(String... params) {
            HttpURLConnection connection = null;
            ArrayList<News> result = new ArrayList<News>();
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    String json = IOUtils.toString(connection.getInputStream(), "UTF-8");
                    JSONObject root = new JSONObject(json);
                    JSONArray articles = root.getJSONArray("articles");

                    for (int i = 0; i < articles.length(); i++) {

                        JSONObject articleJSON = articles.getJSONObject(i);

                        News news = new News();
                        news.title = articleJSON.getString("title");
                        news.author = articleJSON.getString("author");
                        news.publishedAt = articleJSON.getString("publishedAt");
                        news.urlToImage = articleJSON.getString("urlToImage");
                        news.url = articleJSON.getString("url");
                        result.add(news);

                    }

                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return result;
        }


        @Override
        protected void onPostExecute(final ArrayList<News> news) {
            pb_loadingNews.setVisibility(View.INVISIBLE);

            ListView lv_articles = findViewById(R.id.lv_articles);

            NewsAdapter newsAdapter = new NewsAdapter(NewsActivity.this, R.layout.news_item, news);

            // give adapter to ListView UI element to render
            lv_articles.setAdapter(newsAdapter);

            lv_articles.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(NewsActivity.this, WebViewActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("news", news.get(position));
                    intent.putExtra("toWebView", bundle);
                    startActivity(intent);
                }
            });
        }
    }
}
