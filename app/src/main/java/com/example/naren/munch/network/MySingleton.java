package com.example.naren.munch.network;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by naren on 2015-08-22.
 */
public class MySingleton {
    private static MySingleton mInstance;
    private RequestQueue mRequestQueue;

    private MySingleton(Application context) {
        mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized MySingleton getInstance(Application application) {
        if (mInstance == null) {
            mInstance = new MySingleton(application);
        }
        return mInstance;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        mRequestQueue.add(req);
    }
}
