package com.muhammad.muzamil.galleryimagehandlingwithrecyclerview;

import android.net.Uri;

/**
 * Created by Muzamil on 27-Dec-16.
 */
public class Image {
    private Uri url;
    private String strUrl;
    private String name;

    public Image() {
    }

    public Image(Uri url, String strUrl, String name) {
        this.url = url;
        this.strUrl = strUrl;
        this.name = name;
    }

    public Uri getUrl() {
        return url;
    }

    public void setUrl(Uri url) {
        this.url = url;
    }

    public String getStrUrl() {
        return strUrl;
    }

    public void setStrUrl(String strUrl) {
        this.strUrl = strUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
