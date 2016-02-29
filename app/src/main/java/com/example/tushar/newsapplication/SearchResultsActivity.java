package com.example.tushar.newsapplication;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by tushar on 28/2/16.
 */

public class SearchResultsActivity extends AppCompatActivity {

    private TextView txtQuery;
    private RecyclerView.Adapter mSearchAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    ArrayList<NewsFeed> newsList;
    ArrayList<NewsFeed> mySearchList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        mRecyclerView = (RecyclerView) findViewById(R.id.new_search_list);
        // get the action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.search_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        newsList = (ArrayList<NewsFeed>) getIntent().getSerializableExtra("mylist");
        mySearchList=new ArrayList<NewsFeed>();
        mySearchList=handleIntent(getIntent());

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mSearchAdapter = new NewHeadlinesAdapter(mySearchList);
        mRecyclerView.setAdapter(mSearchAdapter);

        ((NewHeadlinesAdapter)mSearchAdapter).setOnItemClickListener(new NewHeadlinesAdapter.CustomClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                String URL=mySearchList.get(position).getHref();
                URL=URL.replaceAll("\n","");
                URL=URL.replaceAll(" ","");
                startIntent(URL);
            }
        });



    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private ArrayList<NewsFeed> handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            for (int i = 0; i < newsList.size() ; i++) {
                NewsFeed element= newsList.get(i);
                if(element.getTitle().toLowerCase().contains(query.toLowerCase())){
                    mySearchList.add(element);
                }
            }
        }
        return mySearchList;
    }

    private void startIntent(String url) {
        Intent webActivity=new Intent(SearchResultsActivity.this,WebViewActivity.class);
        webActivity.putExtra("URL",url);
        //webActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(webActivity);
    }

}