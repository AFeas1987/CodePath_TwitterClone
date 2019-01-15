package com.codepath.apps.twitterclone.api;

import android.app.Application;
import android.content.Context;

import com.codepath.apps.twitterclone.api.network.TwitterClient;

import io.realm.Realm;

//import com.facebook.stetho.Stetho;

public class TwitterApplication extends Application {

    private static TweetRepository tweetRepo;

    @Override
    public void onCreate() {
        super.onCreate();
        // use chrome://inspect to inspect your SQL database
//        Stetho.initializeWithDefaults(this);
        Realm.init(this);
        tweetRepo = new TweetRepository(this);
    }
//
    public static TwitterClient getRestClient(Context context) {
        return (TwitterClient) TwitterClient.getInstance(TwitterClient.class, context);
    }

    public static TweetRepository getTweetRepo() {
        return tweetRepo;
    }
}