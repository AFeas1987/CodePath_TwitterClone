package com.codepath.apps.twitterclone;

import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.twitterclone.adapters.TweetAdapter;
import com.codepath.apps.twitterclone.models.TweetViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class TimelineActivity extends AppCompatActivity {

    private RecyclerView rvTweets;
    private TweetAdapter adapter;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        TweetViewModel viewModel = ViewModelProviders.of(this).get(TweetViewModel.class);
        rvTweets = findViewById(R.id.rvTweets);
        rvTweets.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TweetAdapter(this);
        viewModel.tweets.observe(this, tweets -> adapter.submitList(tweets));
        rvTweets.setAdapter(adapter);
        swipeContainer = findViewById(R.id.swipeContainer);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        swipeContainer.setOnRefreshListener(() -> {
//            viewModel.wipeData();
//            viewModel.invalidate();
            swipeContainer.setRefreshing(false);
        });
    }
}
