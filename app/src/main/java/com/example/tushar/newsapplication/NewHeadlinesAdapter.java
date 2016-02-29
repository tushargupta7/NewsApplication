package com.example.tushar.newsapplication;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by tushar on 28/2/16.
 */
public class NewHeadlinesAdapter extends RecyclerView.Adapter<NewHeadlinesAdapter.ViewHolder> {
    private ArrayList<String> mDataset;
    private ArrayList<NewsFeed> mNewsFeed;
    private AdapterView.OnItemClickListener listener;
    private static CustomClickListener customClickListener;
    private int mPosition;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View
            .OnClickListener{
        public TextView mNewHeadlines;
        public TextView mDescription;
        public CardView cardView;
        public String mHref=null;
        private AdapterView.OnItemClickListener listener;
        public ViewHolder(View v) {
            super(v);
            mNewHeadlines = (TextView) v.findViewById(R.id.info_text);
          //  mDescription= (TextView)v.findViewById(R.id.desc_text);
            cardView = (CardView)v.findViewById(R.id.card_view);
            cardView.setOnClickListener(this);
        }



        @Override
        public void onClick(View v) {
            customClickListener.onItemClick(getAdapterPosition(), v);
        }
    }
    public void setOnItemClickListener(CustomClickListener customClickListener) {
        this.customClickListener = customClickListener;
    }

    public NewHeadlinesAdapter(ArrayList<NewsFeed> Dataset) {
        mNewsFeed=Dataset;
        //mDataset = myDataset;
    }

    @Override
    public NewHeadlinesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_view, parent, false);
        ViewHolder vh = new ViewHolder( v);
        return vh;
    }




    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        mPosition=position;
        String text="<a href='"+ mNewsFeed.get(position).getHref()+"'>"+ mNewsFeed.get(position).getTitle()+ "</a>";
        holder.mNewHeadlines.setText(Html.fromHtml(text));
        holder.mHref=mNewsFeed.get(position).getHref();
    }

    @Override
    public int getItemCount() {
        return mNewsFeed.size();
    }

    public interface CustomClickListener {
        public void onItemClick(int position, View v);
    }

}
