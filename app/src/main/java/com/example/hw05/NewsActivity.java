package com.example.hw05;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        pb_loadingNews = findViewById(R.id.pb_loadingNews);

        extrasFromMain = getIntent().getExtras().getBundle("toNews");
        Source selectedSource = (Source) extrasFromMain.getSerializable("sourceName");
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
                Log.d("demo", "doInBackground: " + url);
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
        protected void onPostExecute( ArrayList<News> news) {
            pb_loadingNews.setVisibility(View.INVISIBLE);

            ListView lv_articles = findViewById(R.id.lv_articles);

            ArrayAdapter ad = new ArrayAdapter(NewsActivity.this,
                    android.R.layout.simple_list_item_1, news);

            // give adapter to ListView UI element to render
            lv_articles.setAdapter(ad);

            for (int i = 0; i < news.size(); i++) {
                final TextView tv_source = new TextView(NewsActivity.this);
                tv_source.setTypeface(null, Typeface.BOLD);
                tv_source.setTextSize(24);
                tv_source.setPadding(20,20,20,20);
                tv_source.setBackgroundResource(R.drawable.tv_border);
                tv_source.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                tv_source.setText(news.get(i).title);
                //lv_articles.addView(tv_source);

                final int finalI = i;
                //  tv_source.setOnClickListener(new View.OnClickListener() {
                //  @Override
                //  public void onClick(View view) {
                        /*Intent intent = new Intent(MainActivity.this, NewsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("sourceName", sources.get(finalI));
                        intent.putExtra("toNews", bundle);
                        Log.d("demo", "onClick: "+ tv_source.getText());
                        startActivity(intent);*/
                //  }
                //  });
            }
            pb_loadingNews.setVisibility(View.INVISIBLE);
            Log.d("demo", "onPostExecute: " + news.toString());
        }
    }
    }
