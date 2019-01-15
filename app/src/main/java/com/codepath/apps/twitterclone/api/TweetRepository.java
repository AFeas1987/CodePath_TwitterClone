package com.codepath.apps.twitterclone.api;

import android.content.Context;
import android.util.Log;

import com.codepath.apps.twitterclone.api.network.TweetNetworkService;
import com.codepath.apps.twitterclone.api.network.TwitterClient;
import com.codepath.apps.twitterclone.ui.recView.models.Tweet;
import com.codepath.apps.twitterclone.ui.recView.util.RealmLiveData;

import io.realm.Realm;

public class TweetRepository {
    public RealmLiveData<Tweet> tweets;
    private TweetNetworkService netService;
    private static long SESSION_MAX;

    public TweetRepository(Context context) {
        TwitterClient client = TwitterApplication.getRestClient(context);
        netService = new TweetNetworkService(client);
        loadMoreTweets(true);
        getAllTweets();
    }

    public void loadMoreTweets(boolean refresh){
        netService.refreshTimeline(SESSION_MAX = refresh ? 0 : SESSION_MAX);
    }

    public RealmLiveData<Tweet> getAllTweets() {
        return tweets != null ? tweets : (tweets =
                new RealmLiveData<>(Realm.getDefaultInstance().where(Tweet.class).sort("uid").findAllAsync()));
    }

    public static void appendToRealm(Tweet... tweets) {
        try(Realm realmInstance = Realm.getDefaultInstance()) {
            realmInstance.executeTransaction((realm) -> {
                for (Tweet t : tweets) {
                    realm.insertOrUpdate(t);
                    SESSION_MAX = t.uid > SESSION_MAX ? t.uid : SESSION_MAX;
                }
            });
            Log.d("_AF", "@@@@@@@@@  Tweets added to Realm!  @@@@@@@@");
        }
    }
}
