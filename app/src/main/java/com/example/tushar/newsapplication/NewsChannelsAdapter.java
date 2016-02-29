package com.example.tushar.newsapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by tushar on 27/2/16.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.tushar.newsapplication.R;

import java.util.ArrayList;

public class NewsChannelsAdapter extends BaseAdapter{
        private Context mContext;
        private ArrayList<String> mBookNameList;
        private ArrayList<String> mNewsArrayList;
        private ArrayList<Integer> imageList;
        public NewsChannelsAdapter(Context c,ArrayList<Integer> news) {
            mContext = c;
            imageList=news;
          // mNewsArrayList=news;
        }

        public int getCount() {
            return imageList.size();
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            View grid;
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {
                grid = new View(mContext);
                grid = inflater.inflate(R.layout.newschannel_grid_element, null);
               // TextView textView = (TextView) grid.findViewById(R.id.grid_text);
                //ImageView imageView = (ImageView)grid.findViewById(R.id.grid_image);
                ImageView imageView=(ImageView)grid.findViewById(R.id.grid_image);
                imageView.setImageResource(imageList.get(position));
                //textView.setText(mNewsArrayList.get(position));
                //imageView.setImageResource(R.drawable.onenote);
            } else {
                grid = (View) convertView;
            }

            return grid;
        }

  // references to our images
  /*  private ArrayList<String> mThumbIds = {
         "ABcd","ejdsk","ahsjs"
    };*//*
*/
}
