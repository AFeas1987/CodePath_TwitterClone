package com.codepath.apps.twitterclone.ui.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.codepath.apps.twitterclone.R;
import com.codepath.apps.twitterclone.api.TwitterApplication;
import com.codepath.apps.twitterclone.ui.recView.adapters.TweetRealmAdapter;
import com.codepath.apps.twitterclone.ui.recView.util.TweetViewModel;


import io.realm.Sort;

public class TimelineActivity extends AppCompatActivity {

    private RecyclerView rvTweets;
    private TweetRealmAdapter adapter;
    private SwipeRefreshLayout swipeContainer;
//    private LiveData<>

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        TweetViewModel viewModel = ViewModelProviders.of(this).get(TweetViewModel.class);
        rvTweets = findViewById(R.id.rvTweets);
        LinearLayoutManager llMgr = new LinearLayoutManager(this);
        rvTweets.setLayoutManager(llMgr);
        rvTweets.addItemDecoration(new DividerItemDecoration(
                rvTweets.getContext(), llMgr.getOrientation()));
        adapter = new TweetRealmAdapter(this, viewModel.tweets.getResults()
                .sort("uid", Sort.DESCENDING), true);
        rvTweets.setAdapter(adapter);
        viewModel.tweets.observe(this, res -> {
//            adapter.refreshVisibleData(rvTweets);
            Log.d("_AF", "LiveData refresh observed in Activity.  Tweets: " + adapter.getItemCount());
        });
        swipeContainer = findViewById(R.id.swipeContainer);
        swipeContainer.setColorSchemeResources(
                android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        rvTweets.setOnScrollChangeListener(adapter.createScrollListener(rvTweets));
        swipeContainer.setOnRefreshListener(() -> {
            if (!adapter.getItem(0).createdRecently())
                TwitterApplication.getTweetRepo().getNewTweets(0);
            adapter.refreshVisibleData(rvTweets);
            swipeContainer.setRefreshing(false);
        });
    }
}
