package com.codepath.apps.twitterclone.api.network;

import android.util.Log;

import com.codepath.apps.twitterclone.api.TweetRepository;
import com.codepath.apps.twitterclone.ui.recView.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class TweetNetworkService {

    private final TwitterClient client;

    public TweetNetworkService(TwitterClient client) {
        this.client = client;
    }

    public void refreshTimeline(Long max) {
        client.getHomeTimeline(max, getHandler(true));
    }


    private JsonHttpResponseHandler getHandler(boolean isAsync) {

        JsonHttpResponseHandler handler = new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("_AF", "onSuccess of tweet handler\n\t" + response.toString());
                List<Tweet> tweets = new ArrayList<>();
                for (int i = 0; i < response.length(); ++i)
                    try {
                        Tweet t = Tweet.fromJson(response.getJSONObject(i));
                        tweets.add(t);
                        TweetRepository.appendToRealm(t);
                    }
                    catch (JSONException e) {e.printStackTrace();}
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("_AF", "!!!!!!!!!!!!!!   onFailure: " + errorResponse.toString());
            }
        };

        if (isAsync) {
            handler.setUseSynchronousMode(true);
            handler.setUsePoolThread(true);
        }

        return handler;
    }
}
