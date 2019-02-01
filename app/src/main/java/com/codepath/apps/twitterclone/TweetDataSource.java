package com.codepath.apps.twitterclone;

import androidx.paging.ItemKeyedDataSource;
import androidx.annotation.NonNull;
import android.util.Log;

import com.codepath.apps.twitterclone.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class TweetDataSource extends ItemKeyedDataSource<Long, Tweet> {

    TwitterClient client;

    public TweetDataSource(TwitterClient client) {
        this.client = client;
    }

    @Override
    public void loadInitial(@NonNull ItemKeyedDataSource.LoadInitialParams<Long> params, @NonNull LoadInitialCallback<Tweet> callback) {
        Log.d("_AF", "loadInitial called!!!!!!!!!");
        JsonHttpResponseHandler handler = createTweetHandler(callback, true);
        client.getHomeTimeline(0, handler);
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Tweet> callback) {
        Log.d("_AF", "loadAfter called!!!!!!!!!");
        JsonHttpResponseHandler handler = createTweetHandler(callback, true);
        client.getHomeTimeline(params.key - 1, handler);
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Tweet> callback) {}

    @NonNull
    @Override
    public Long getKey(@NonNull Tweet item) {
        return item.uid;
    }

    public JsonHttpResponseHandler createTweetHandler(final LoadCallback<Tweet> callback, boolean isAsync) {

        JsonHttpResponseHandler handler = new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                Log.d("_AF", "onSuccess of tweet handler\n\t" + response.toString());

                List<Tweet> tweets = new ArrayList<>();
                for (int i = 0; i < response.length(); ++i)
                    try {
                        tweets.add(Tweet.fromJson(response.getJSONObject(i)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                // send back to PagedList handler
                callback.onResult(tweets);
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
