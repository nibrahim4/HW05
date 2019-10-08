package com.example.hw05;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.Toast;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static android.widget.AdapterView.*;

public class MainActivity extends AppCompatActivity {

    public ProgressBar pb_loadingSources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Main Activity");
        
        pb_loadingSources = findViewById(R.id.pb_loadingSources);
        pb_loadingSources.setVisibility(View.VISIBLE);

        if (isConnected()) {

          new GetSourcesAsync().execute("https://newsapi.org/v2/sources?apiKey=2cc75c20cb4e4b72b089efd93f3cc33a");

        } else {
            pb_loadingSources.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "No Internet Connection!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() ||
                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                        && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            return false;
        }
        return true;
    }

    private class GetSourcesAsync extends AsyncTask<String, Void, ArrayList<Source>> {

        @Override
        protected ArrayList<Source> doInBackground(String... params) {
            HttpURLConnection connection = null;
            ArrayList<Source> result = new ArrayList<Source>();
            try {
                URL url = new URL(params[0]);
                Log.d("demo", "doInBackground: " + url);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    String json = IOUtils.toString(connection.getInputStream(), "UTF-8");
                    JSONObject root = new JSONObject(json);
                    JSONArray sources = root.getJSONArray("sources");

                    for (int i = 0; i < sources.length(); i++) {

                        JSONObject articleJSON = sources.getJSONObject(i);

                        Source source = new Source();
                        source.id = articleJSON.getString("id");
                        source.name= articleJSON.getString("name");

                        result.add(source);
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
        protected void onPostExecute(final ArrayList<Source> sources) {
            pb_loadingSources.setVisibility(View.INVISIBLE);

            ListView lv_sources = findViewById(R.id.lv_sources);

            final ArrayList<String> sourceNames = new ArrayList<String>();


            for (int i=0; i< sources.size(); i++){
              sourceNames.add(sources.get(i).name);

            }

            ArrayAdapter ad = new ArrayAdapter(MainActivity.this,
                    android.R.layout.simple_list_item_1, sourceNames);

            // give adapter to ListView UI element to render
            lv_sources.setAdapter(ad);


            lv_sources.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                    Intent intent = new Intent(MainActivity.this, NewsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("sourceName", sources.get(position));
                    intent.putExtra("toNews", bundle);
                    startActivity(intent);
                }
            });
        }


    }
}