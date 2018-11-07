package edu.psu.slparker.loyaltyapp;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.List;

public class GridViewAdapter extends BaseAdapter {
    Context mContext;

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if(convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setPadding(8, 8, 8, 8);
            imageView.setImageResource(R.drawable.ic_menu_rewards);
            imageView.setBackgroundColor(Color.WHITE);
        } else {
            imageView = (ImageView) convertView;
        }
        return imageView;
    }
}
