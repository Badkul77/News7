package com.example.news7.volley;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
/*
* If your application makes constant use of the network,
 * it's probably most efficient to set up a single instance of RequestQueue that will last the lifetime of your app.
 * You can achieve this in various ways.
 * The recommended approach is to implement a singleton class that encapsulates RequestQueue and other Volley functionality.
  * Another approach is to subclass Application and set up the RequestQueue in Application.onCreate().
  * But this approach is discouraged; a static singleton can provide the same functionality in a more modular way.

A key concept is that the RequestQueue must be instantiated with the Application context, not an Activity context.
This ensures that the RequestQueue will last for the lifetime of your app, instead of being recreated every time the activity is recreated
(for example, when the user rotates the device).*/
public class MySingleton {
    private static MySingleton instance;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;//The Thumbnails should be stored in a cache memory, for that I am using the Universal Image Loader
    private static Context ctx;

    private MySingleton(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();

        imageLoader = new ImageLoader(requestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }

    public static synchronized MySingleton getInstance(Context context) {
        if (instance == null) {
            instance = new MySingleton(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }
}
