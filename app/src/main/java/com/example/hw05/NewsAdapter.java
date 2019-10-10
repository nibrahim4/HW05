package com.example.hw05;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(@NonNull Context context, int resource, @NonNull List<News> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        News news = getItem(position);

        if(convertView == null){
          convertView = LayoutInflater.from(getContext()).inflate(R.layout.news_item,parent, false);
        }

        TextView tv_title = convertView.findViewById(R.id.tv_title);
        if(news.title != null && !news.title.equals("null")){
            tv_title.setText(news.title);
        }

        TextView tv_author = convertView.findViewById(R.id.tv_author);
        if(news.author != null && !news.author.equals("null")){
            tv_author.setText(news.author);
        }

        TextView tv_publishedDate = convertView.findViewById(R.id.tv_newsDate);
        if(news.publishedAt != null && !news.publishedAt.equals("null")){
            tv_publishedDate.setText(news.publishedAt);
        }

        try{
            ImageView iv_urlToImage = convertView.findViewById(R.id.iv_urlToImage);
            if(news.urlToImage != null || !news.urlToImage.equals("")){
                Picasso.with(convertView.getContext()).load(news.urlToImage).into(iv_urlToImage);
            }else{
                Toast.makeText(convertView.getContext(), "No image to load!", Toast.LENGTH_SHORT).show();
            }
        }catch(Exception e){
            Toast.makeText(convertView.getContext(), "No image to load!", Toast.LENGTH_SHORT).show();
        }


        return convertView;
    }
}
