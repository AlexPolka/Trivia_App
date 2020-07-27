package com.darclabs.trivia.controller;

import android.app.Application;
import android.text.TextUtils;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

//Will govern the application as a whole
public class AppController extends Application {
    //Used to get the name of the class when logging/debugging
    public static final String TAG = AppController.class
                .getSimpleName();
    private static AppController mInstance;
    private RequestQueue mRequestQueue;

    //The actual app controller
    public static synchronized AppController getInstance(){

        return mInstance;
    }

    //This is where we get the instance and set it up.
    @Override
    public void onCreate(){
        super.onCreate();
        mInstance = this;
    }

    //Creating the request queue
    public RequestQueue getRequestQueue(){
        if (mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    //Add to request queue
    public <T> void addToRequestQueue(Request<T> req, String tag){
        //set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG: tag);
        getRequestQueue().add(req);
    }
    //overloading method
    public <T> void addToRequestQueue(Request<T> req){
        req.setTag(TAG);
        getRequestQueue().add(req);
    }
    //not used in this application but can be useful
    public void cancelPendingRequests(Object tag){
        if (mRequestQueue != null){
            mRequestQueue.cancelAll(tag);
        }
    }


}
