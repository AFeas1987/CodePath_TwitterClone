package com.codepath.apps.twitterclone.ui.recView.util;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.codepath.apps.twitterclone.api.TweetRepository;
import com.codepath.apps.twitterclone.api.TwitterApplication;
import com.codepath.apps.twitterclone.ui.recView.models.Tweet;


public class TweetViewModel extends AndroidViewModel {
    public RealmLiveData<Tweet> tweets;

    public TweetViewModel(@NonNull Application application) {
        super(application);
        TweetRepository repo = TwitterApplication.getTweetRepo();
        tweets = repo.getAllTweetsFromRealm();
    }
}
