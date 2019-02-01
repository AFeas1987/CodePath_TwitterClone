package com.codepath.apps.twitterclone.models;

import android.app.Application;

import com.codepath.apps.twitterclone.TweetDataSourceFactory;
import com.codepath.apps.twitterclone.TwitterApplication;
import com.codepath.apps.twitterclone.TwitterClient;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

public class TweetViewModel extends AndroidViewModel {
    public LiveData<PagedList<Tweet>> tweets;
    public DataSource<Long, Tweet> source;


    public TweetViewModel(@NonNull Application application) {
        super(application);
        wipeData();
    }

    public void invalidate(){
        source.invalidate();
    }

    public void wipeData(){
        TwitterClient client = TwitterApplication.getRestClient(getApplication().getApplicationContext());
        TweetDataSourceFactory factory = new TweetDataSourceFactory(client);
        PagedList.Config config = new PagedList.Config.Builder().setPageSize(25)
                .setEnablePlaceholders(true).setInitialLoadSizeHint(50).build();
        source = factory.create();
        tweets = new MutableLiveData<>();
        tweets = new LivePagedListBuilder<>(factory, config).build();
    }
}
