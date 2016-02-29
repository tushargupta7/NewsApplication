package com.example.tushar.newsapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    /*private String EtURL="http://economictimes.indiatimes.com/rssfeedstopstories.cms";
    private String ToiUrl="http://dynamic.feedsportal.com/pf/555218/http://toi.timesofindia.indiatimes.com/rssfeedstopstories.cms";
    private String IndExpUrl="http://syndication.indianexpress.com/rss/798/latest-news.xml";
    private String piourl="http://www.pioneer.org/1/feed";
    private String decurl="http://www.deccanherald.com/rss-internal/top-stories.rss";*/
    ArrayList<Integer> imageArray;
    ArrayList<String> newsArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createNewsArray();
        createImage();
        setContentView(R.layout.activity_main);
        GridView gridview = (GridView) findViewById(R.id.news_channel_gridview);
        gridview.setAdapter(new NewsChannelsAdapter(this,imageArray));
        //gridview.setAdapter(new NewsChannelsAdapter(this,newsArray));
        gridview.setVerticalSpacing(30);
        gridview.setNumColumns(2);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(MainActivity.this,"Please Wait While Loading...",Toast.LENGTH_SHORT).show();
                String NewChannelName= newsArray.get(position);
                startHeadLinesActivity(NewChannelName);
            }
        });
    }

    private void startHeadLinesActivity(String newChannelName) {
        String newUrl=null;
        switch(newChannelName){
            case "Times Of India": { newUrl=Constants.ToiUrl;
                            break;
            }
            case "Economic Times": {
                newUrl=Constants.EtURL;
                break;
            }
            case "Indian Express": {
                newUrl=Constants.IndExpUrl;
                break;
            }
            case "Pioneer": {
                newUrl=Constants.piourl;
                break;
            }
            case "Deccan": {
                newUrl=Constants.decurl;
                break;
            }
            default:newUrl=null;
        }
        Intent intent = new Intent(MainActivity.this,
                NewHeadlineActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("NewsChannelUrl",newUrl);
        startActivity(intent);
    }

    private ArrayList<String> createNewsArray() {
        newsArray=new ArrayList<String>();
        newsArray.add("Times Of India");
        newsArray.add("Pioneer");
        newsArray.add("Economic Times");
    //    newsArray.add("Indian Express");
     //   newsArray.add("Pioneer");
     //   newsArray.add("Deccan");
        return newsArray;
    }
    private ArrayList<Integer> createImage(){
        imageArray=new ArrayList<Integer>();
        imageArray.add(R.drawable.timeofindia);
        imageArray.add(R.drawable.pi);
        imageArray.add(R.drawable.et);
        return imageArray;
    }

}
