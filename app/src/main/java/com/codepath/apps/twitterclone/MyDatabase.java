package com.codepath.apps.twitterclone;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.codepath.apps.twitterclone.models.Tweet;
import com.codepath.apps.twitterclone.models.SampleModelDao;

@Database(entities={Tweet.class}, version=1)
public abstract class MyDatabase extends RoomDatabase {
    public abstract SampleModelDao sampleModelDao();

    // Database name to be used
    public static final String NAME = "MyDataBase";
}
