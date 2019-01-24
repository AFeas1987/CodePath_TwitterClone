package com.codepath.apps.twitterclone.api.network;

import android.util.Log;

import com.codepath.apps.twitterclone.api.TweetRepository;
import com.codepath.apps.twitterclone.ui.recView.models.Tweet;
import com.codepath.apps.twitterclone.ui.recView.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import io.realm.Realm;
import io.realm.exceptions.RealmException;

public class TweetNetworkService {

    private final TwitterClient client;
    public static long SESSION_MAX;
    private NetworkLiveData netStatus;

    public TweetNetworkService(TwitterClient client) {
        this.client = client;
        netStatus = new NetworkLiveData();
    }

    public void loadOlderTweets(long after) {
        netStatus.setStatus(NetworkLiveData.NetworkStatus.LOADING);
        client.loadOlderTweets(after, getHandler(true, false));
    }

    public void getNewTweets(long max){
        netStatus.setStatus(NetworkLiveData.NetworkStatus.LOADING);
        client.getNewTweets(max, getHandler(true, true));
    }

    private JsonHttpResponseHandler getHandler(boolean isAsync, boolean isRefresh) {
        JsonHttpResponseHandler handler = new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("_AF", "onSuccess of tweet handler\n\t" + response.toString());
                if (isRefresh)
                    try (Realm realm = Realm.getDefaultInstance()) {
                        realm.executeTransaction(r -> {
                            r.delete(Tweet.class);
                            r.delete(User.class);
                        });
                    } catch (RealmException ex) {Log.e("_AF", ex.getMessage());}
                for (int i = 0; i < response.length(); ++i)
                    try {TweetRepository.appendToRealm(Tweet.fromJson(response.getJSONObject(i)));
                    } catch (JSONException e) {e.printStackTrace();}
                netStatus.setStatus(NetworkLiveData.NetworkStatus.IDLE);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject e) {
                Log.e("_AF", "!!!!!!!!!!!!!!   onFailure: " +
                        (e != null ? e.toString() : "No network detected."));
                netStatus.setStatus(NetworkLiveData.NetworkStatus.IDLE);
            }
        };

        if (isAsync) {
            handler.setUseSynchronousMode(true);
            handler.setUsePoolThread(true);
        }

        return handler;
    }
}
