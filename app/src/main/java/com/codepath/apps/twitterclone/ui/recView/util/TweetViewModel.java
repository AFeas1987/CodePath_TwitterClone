package com.codepath.apps.twitterclone.ui.recView.util;

import android.app.Application;

import com.codepath.apps.twitterclone.api.TwitterApplication;
import com.codepath.apps.twitterclone.ui.recView.models.Tweet;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class TweetViewModel extends AndroidViewModel {
    public RealmLiveData<Tweet> tweets;


    public TweetViewModel(@NonNull Application application) {
        super(application);
        tweets = TwitterApplication.getTweetRepo().getAllTweets();
    }
}
