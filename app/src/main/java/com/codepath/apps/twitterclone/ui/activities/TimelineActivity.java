package com.codepath.apps.twitterclone.ui.activities;

import android.os.Bundle;

import com.codepath.apps.twitterclone.R;
import com.codepath.apps.twitterclone.api.TwitterApplication;
import com.codepath.apps.twitterclone.ui.recView.adapters.TweetRealmAdapter;
import com.codepath.apps.twitterclone.ui.recView.models.Tweet;
import com.codepath.apps.twitterclone.ui.recView.util.TweetViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import io.realm.Realm;
import io.realm.Sort;

public class TimelineActivity extends AppCompatActivity {

    private RecyclerView rvTweets;
    private TweetRealmAdapter adapter;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        TweetViewModel viewModel = ViewModelProviders.of(this).get(TweetViewModel.class);
        rvTweets = findViewById(R.id.rvTweets);
        rvTweets.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TweetRealmAdapter(this, Realm.getDefaultInstance().where(Tweet.class)
                .sort("uid", Sort.DESCENDING).findAllAsync(), true);
        rvTweets.setAdapter(adapter);
        viewModel.tweets.observe(this, res -> {});
        swipeContainer = findViewById(R.id.swipeContainer);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        swipeContainer.setOnRefreshListener(() -> {
            TwitterApplication.getTweetRepo().loadMoreTweets(true);
            swipeContainer.setRefreshing(false);
        });
    }
}
