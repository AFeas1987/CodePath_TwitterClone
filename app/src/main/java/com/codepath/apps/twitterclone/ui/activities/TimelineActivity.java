package com.codepath.apps.twitterclone.ui.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.codepath.apps.twitterclone.R;
import com.codepath.apps.twitterclone.api.TwitterApplication;
import com.codepath.apps.twitterclone.ui.recView.adapters.TweetRealmAdapter;
import com.codepath.apps.twitterclone.ui.recView.util.TweetViewModel;

import io.realm.Sort;

public class TimelineActivity extends AppCompatActivity {

    private final int REQUEST_CODE = 20;
    private RecyclerView rvTweets;
    private TweetRealmAdapter adapter;
    private SwipeRefreshLayout swipeContainer;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.compose:
                Toast.makeText(this, "Compose!", Toast.LENGTH_LONG).show();
                startActivityForResult(new Intent(this, ComposeActivity.class), REQUEST_CODE);
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            rvTweets.smoothScrollToPosition(0);
        }
    }
}
