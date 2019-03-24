package com.ashwin.application.newsapplication;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;


class ListNewsAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;

    public ListNewsAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ListNewsViewHolder holder;
        if (view == null) {
            holder = new ListNewsViewHolder();
            view = LayoutInflater.from(activity).inflate(
                    R.layout.list_row, viewGroup, false);
            holder.galleryImage = (ImageView) view.findViewById(R.id.galleryImage);
            holder.title = (TextView) view.findViewById(R.id.title);
            holder.sdetails = (TextView) view.findViewById(R.id.sdetails);
            view.setTag(holder);
        } else {
            holder = (ListNewsViewHolder) view.getTag();
        }
        holder.galleryImage.setId(i);
        holder.title.setId(i);
        holder.sdetails.setId(i);
        HashMap<String, String> news = new HashMap<String, String>();
        news = data.get(i);
        try{
            holder.title.setText(news.get(MainActivity.KEY_TITLE));
            holder.sdetails.setText(news.get(MainActivity.KEY_DESCRIPTION));
                Picasso.with(activity)
                        .load(news.get(MainActivity.KEY_URLTOIMAGE).toString())
                        .resize(300, 200)
                        .into(holder.galleryImage);

        }
        catch(Exception e) {}
        return view;

    }
}

class ListNewsViewHolder {
    ImageView galleryImage;
    TextView title, sdetails;
}