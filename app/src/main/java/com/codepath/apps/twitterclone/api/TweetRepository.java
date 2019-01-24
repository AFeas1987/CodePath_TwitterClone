package com.codepath.apps.twitterclone.api;

import android.content.Context;
import android.util.Log;

import com.codepath.apps.twitterclone.api.network.TweetNetworkService;
import com.codepath.apps.twitterclone.api.network.TwitterClient;
import com.codepath.apps.twitterclone.ui.recView.models.Tweet;
import com.codepath.apps.twitterclone.ui.recView.util.RealmLiveData;

import io.realm.Realm;
import io.realm.Sort;

import static com.codepath.apps.twitterclone.api.network.TweetNetworkService.SESSION_MAX;

public class TweetRepository {
    public RealmLiveData<Tweet> tweets;
    private TweetNetworkService netService;

    public TweetRepository(Context context) {
        TwitterClient client = TwitterApplication.getRestClient(context);
        netService = new TweetNetworkService(client);
        getAllTweetsFromRealm();
        getNewTweets(0);
    }

    public void loadOlderTweets(long after){
        netService.loadOlderTweets(after);
        getAllTweetsFromRealm();
    }

    public void getNewTweets(long since){
        netService.getNewTweets(since);
        getAllTweetsFromRealm();
    }

    public RealmLiveData<Tweet> getAllTweetsFromRealm() {
        return tweets != null ? tweets : (tweets =
                new RealmLiveData<>(Realm.getDefaultInstance().where(Tweet.class)
                        .sort("uid", Sort.DESCENDING).findAllAsync()));
    }

    public static void appendToRealm(Tweet... tweets) {
        try(Realm realmInstance = Realm.getDefaultInstance()) {
            realmInstance.executeTransactionAsync((realm) -> {
                for (Tweet t : tweets) {
                    if (realm.where(Tweet.class).equalTo("uid", t.uid).findFirst() == null) {
                        realm.insertOrUpdate(t);
                        SESSION_MAX = t.uid > SESSION_MAX ? t.uid : SESSION_MAX;
                        Log.d("_AF",
                                "################  Tweet added to Realm!  ################");
                    }
                }
            });
        }
    }
}
