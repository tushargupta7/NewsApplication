package com.example.tushar.newsapplication;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;
        import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class NewHeadlineActivity extends AppCompatActivity {
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    ArrayList<String> myDataset;
    ArrayList<NewsFeed> mHeadlines;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_headline);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.new_headlines_list);
        Intent newUrlIntent=getIntent();
        String Url=newUrlIntent.getStringExtra("NewsChannelUrl");
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mHeadlines=new ArrayList<NewsFeed>();

        mHeadlines= getNewHeadLines(Url);
        if(mHeadlines==null){
            finish();
            return;
        }
        mAdapter = new NewHeadlinesAdapter(mHeadlines);
        mRecyclerView.setAdapter(mAdapter);
        ((NewHeadlinesAdapter)mAdapter).setOnItemClickListener(new NewHeadlinesAdapter.CustomClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                String URL=mHeadlines.get(position).getHref();
                URL=URL.replaceAll("\n","");
                URL=URL.replaceAll(" ","");
                startIntent(URL);
            }
        });


    }

   /* private NewsFeed[] getNewHeadLines3() {
        org.w3c.dom.Document document = null;
        Ndtv getDoc=new Ndtv();
        getDoc.execute();
        try {
            document=getDoc.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        NewsFeed arr[]=new NewsFeed[100];
        arr=getNdtvArray(document);
        return arr;
    }*/

   /* private NewsFeed[] getNdtvArray(org.w3c.dom.Document document) {
        NodeList items=document.getElementsByTagName("item");
        int len=items.getLength();

        NewsFeed[] newsFeeds=new NewsFeed[len];
        return new NewsFeed[0];
    }
*/

/*
    public class Ndtv extends AsyncTask<Void, Void, org.w3c.dom.Document> {
        DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder builder=null;

        @Override
        protected void onPreExecute() {
            try {
                builder = factory.newDocumentBuilder();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
        }
        @Override
        protected org.w3c.dom.Document doInBackground(Void... params) {
            Document doc = null;

            org.w3c.dom.Document docu = null;
            try {
                doc = Jsoup.connect("http://economictimes.indiatimes.com/rssfeedstopstories.cms").get();
                String a=doc.toString();
                InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(a));

                try {
                    docu= builder.parse(is);
                } catch (SAXException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return docu;
        }

    }
*/


    private ArrayList<NewsFeed> getNewHeadLines(String Url) {
        org.w3c.dom.Document document = null;
        GetNewsHeadlinePageDoc getDoc=new GetNewsHeadlinePageDoc();
        getDoc.execute(Url);
        try {
            document=getDoc.get();
            if(document==null){
                Toast.makeText(NewHeadlineActivity.this,"please Check internet Connection",Toast.LENGTH_LONG);
                return null;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        ArrayList<NewsFeed> arr=new ArrayList<NewsFeed>();
        arr= NewsDetailsArray(document);
        return arr;
    }


    public class GetNewsHeadlinePageDoc extends AsyncTask<String, Void, org.w3c.dom.Document> {
        DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder builder=null;

        @Override
        protected void onPreExecute() {
            try {
                builder = factory.newDocumentBuilder();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected org.w3c.dom.Document doInBackground(String... params) {
            Document doc = null;
            String url=params[0];
            org.w3c.dom.Document docu = null;
            try {
                //doc = Jsoup.connect("http://dynamic.feedsportal.com/pf/555218/http://toi.timesofindia.indiatimes.com/rssfeedstopstories.cms").get();
                doc=Jsoup.connect(url).get();
                String a=doc.toString();
                InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(a));

                try {
                    docu= builder.parse(is);
                } catch (SAXException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return docu;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void startIntent(String url) {
        Intent webActivity=new Intent(NewHeadlineActivity.this,WebViewActivity.class);
        webActivity.putExtra("URL",url);
        //webActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(webActivity);
    }

    private ArrayList<NewsFeed> NewsDetailsArray(org.w3c.dom.Document docu) {
        NodeList items=docu.getElementsByTagName("item");
        int len=items.getLength();
        ArrayList<NewsFeed> newsFeeds=new ArrayList<NewsFeed>();
        for(int i=0;i<len;i++){
            NewsFeed newsFeed=new NewsFeed();
            org.w3c.dom.Node child=items.item(i);
            NodeList subNodeList=child.getChildNodes();
            for(int j=0;j<subNodeList.getLength();j++){
                org.w3c.dom.Node subsub=subNodeList.item(j);
                String name=subsub.getNodeName();
                if(subsub.getNodeType()== org.w3c.dom.Document.ELEMENT_NODE && name!=null){
                    if(name.equalsIgnoreCase("guid")){
                        //link=subsub.getChildNodes().item(0).getTextContent();
                        //newsFeeds[i].setHref(subsub.getChildNodes().item(0).getTextContent());;
                       newsFeed.setHref(subsub.getChildNodes().item(0).getTextContent());
                    }
                    else if(name.equalsIgnoreCase("title")){
                        //Title=subsub.getChildNodes().item(0).getTextContent();
                        newsFeed.setTitle(subsub.getChildNodes().item(0).getTextContent());
                    }
                }
                else{
                  continue;
                }

            }
         newsFeeds.add(newsFeed);
        }
        return newsFeeds;
    }

/*
    private NewsFeed[] getNewHeadLines1() {

        DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder builder=null;
        String Title = null;
        String link;
        try {
             builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document doc = null;
        org.w3c.dom.Document docu = null;
        try {
            doc = Jsoup.connect("http://dynamic.feedsportal.com/pf/555218/http://toi.timesofindia.indiatimes.com/rssfeedstopstories.cms").get();
            String a=doc.toString();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(a));

            try {
                docu= builder.parse(is);
            } catch (SAXException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        NodeList items=docu.getElementsByTagName("item");
        int len=items.getLength();
        NewsFeed[] newsFeeds=new NewsFeed[len];
        for(int i=0;i<len;i++){
            newsFeeds[i]=new NewsFeed();
            org.w3c.dom.Node child=items.item(i);
            NodeList subNodeList=child.getChildNodes();
            for(int j=0;j<subNodeList.getLength();j++){
                org.w3c.dom.Node subsub=subNodeList.item(j);
                String name=subsub.getNodeName();
                if(subsub.getNodeType()== org.w3c.dom.Document.ELEMENT_NODE && name!=null){
                    if(name.equalsIgnoreCase("guid")){
                        link=subsub.getChildNodes().item(0).getTextContent();
                        newsFeeds[i].href=link;
                    }
                    else if(name.equalsIgnoreCase("title")){
                        Title=subsub.getChildNodes().item(0).getTextContent();
                        newsFeeds[i].title=Title;
                    }
                    else if(name.equalsIgnoreCase("description")){
                        newsFeeds[i].desc=subsub.getChildNodes().item(0).getTextContent();
                    }
                }

            }

        }

        return newsFeeds;
    }
*/


@Override
public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.options_menu, menu);
    SearchManager searchManager =
            (SearchManager) getSystemService(Context.SEARCH_SERVICE);
    SearchView searchView =
            (SearchView) menu.findItem(R.id.search).getActionView();
    searchView.setSearchableInfo(
            searchManager.getSearchableInfo(getComponentName()));
    return true;
}

    @Override
    public void startActivity(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {

            intent.putExtra("mylist", mHeadlines);
        }
        super.startActivity(intent);
    }
}
