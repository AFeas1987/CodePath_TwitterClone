package com.codepath.apps.twitterclone;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.codepath.apps.twitterclone.models.Tweet;

public class TweetDataSourceFactory extends DataSource.Factory<Long, Tweet> {

    TwitterClient client;
    private MutableLiveData<TweetDataSource> mSourceLiveData =
            new MutableLiveData<>();

    public TweetDataSourceFactory(TwitterClient client) {
        this.client = client;
    }

    @Override
    public DataSource<Long, Tweet> create() {
        TweetDataSource source = new TweetDataSource(client);
        mSourceLiveData.postValue(source);
        return source;
    }
}