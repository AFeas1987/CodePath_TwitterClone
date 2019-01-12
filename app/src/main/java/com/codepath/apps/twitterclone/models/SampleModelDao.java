package com.codepath.apps.twitterclone.models;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface SampleModelDao {

    // @Query annotation requires knowing SQL syntax
    // See http://www.sqltutorial.org/
    
//    @Query("SELECT * FROM Tweet WHERE id = :id")
//    Tweet byId(long id);
//
//    @Query("SELECT * FROM Tweet ORDER BY ID DESC LIMIT 300")
//    List<Tweet> recentItems();
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    void insertModel(Tweet... tweets);
}
