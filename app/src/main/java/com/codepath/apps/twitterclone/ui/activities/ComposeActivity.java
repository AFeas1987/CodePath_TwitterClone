package com.codepath.apps.twitterclone.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.apps.twitterclone.R;
import com.codepath.apps.twitterclone.api.TweetRepository;
import com.codepath.apps.twitterclone.api.TwitterApplication;
import com.codepath.apps.twitterclone.ui.recView.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {

    private final int REQUEST_CODE = 20;

    private final int MAX_LENGTH = 140;

    private EditText etCompose;
    private Button btnTweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        etCompose = findViewById(R.id.etCompose);
        btnTweet = findViewById(R.id.btnTweet);

        btnTweet.setOnClickListener(v -> {
            String content = etCompose.getText().toString();
            if (content.isEmpty()) {
                Toast.makeText(ComposeActivity.this, "Say something first!", Toast.LENGTH_LONG).show();
                return;
            }
            // make Api call
            TwitterApplication.getRestClient(this).composeTweet(content, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    Log.d("_AF", "onSuccess: SUCCESSFULLY POSTED TWEET!");
                    try {
                        Tweet tweet = Tweet.fromJson(response);
                        TweetRepository.appendToRealm(tweet);
                        setResult(RESULT_OK, new Intent().putExtra("tweet", Parcels.wrap(tweet)));
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.d("_AF", "onFailure: " + responseString);
                    super.onFailure(statusCode, headers, responseString, throwable);
                }
            });
        });
    }
}
