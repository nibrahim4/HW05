package com.example.hw05;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
        tv_title.setText(news.title);

        TextView tv_author = convertView.findViewById(R.id.tv_author);
        tv_author.setText(news.author);

        return convertView;
    }
}
