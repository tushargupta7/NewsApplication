package com.example.tushar.newsapplication;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by tushar on 29/2/16.
 */
public class NewsFeed implements Serializable{


       private String title;
       private String href;
     //  private String desc;



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }


}
