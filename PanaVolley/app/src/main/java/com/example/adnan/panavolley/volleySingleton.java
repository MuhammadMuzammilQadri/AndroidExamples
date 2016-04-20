package com.example.adnan.panavolley;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Muzammil on 10/22/2015.
 */

//Taken from slidenerd's videos (youtube)
public class volleySingleton {


    public static volleySingleton sInstance = null;
    RequestQueue mRequestQueue;
    private ImageLoader imgLoad;

    public ImageLoader getImgLoad() {
        return imgLoad;
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    private volleySingleton() {
        mRequestQueue = Volley.newRequestQueue(myApplication.getAppContext());
        imgLoad = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
            private LruCache<String, Bitmap> lru = new LruCache<String, Bitmap>((int) (Runtime.getRuntime().maxMemory() / 1024 / 8));

            @Override
            public Bitmap getBitmap(String s) {
                return lru.get(s);
            }

            @Override
            public void putBitmap(String s, Bitmap bitmap) {
                lru.put(s, bitmap);
            }
        });
    }

    public static volleySingleton getInstance() {
        if (sInstance == null) {
            sInstance = new volleySingleton();
        }
        return sInstance;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

}

