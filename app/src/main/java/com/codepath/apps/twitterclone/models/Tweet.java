package com.codepath.apps.twitterclone.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import android.icu.text.SimpleDateFormat;
import android.text.format.DateUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Locale;

/*
 * This is a temporary, sample model that demonstrates the basic structure
 * of a SQLite persisted Model object. Check out the Room guide for more details:
 * https://github.com/codepath/android_guides/wiki/Room-Guide
 *
 */
@Entity
public class Tweet {

	@PrimaryKey(autoGenerate = true)
	public Long uid;

	// Define table fields
	@ColumnInfo
    public String createdAt;

	@Ignore
	public User user;
	public String body;

	public Tweet() {}

	// Parse model from JSON
	public static Tweet fromJson(JSONObject object) throws JSONException{
		Tweet tweet = new Tweet();
		tweet.body = object.getString("text");
		tweet.uid = object.getLong("id");
		tweet.createdAt = getRelativeTimeAgo(object.getString("created_at"));
		tweet.user = User.fromJson(object.getJSONObject("user"));

		Log.d("_AF", "~~~~~~~~ " + tweet.uid + ", " + tweet.createdAt +
                ":\n\t\t" + tweet.user.screenName + ":  " + tweet.body);

		return tweet;
	}

	private static String getRelativeTimeAgo(String rawJsonDate) {
		String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
		SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
		sf.setLenient(true);

		String relativeDate = "";
		try {
			long dateMillis = sf.parse(rawJsonDate).getTime();
			relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
					System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return relativeDate.replace(" ago", "")
                .replace(" Yesterday", "1d")
                .replace(" days", "d")
                .replace(" hours", "h")
                .replace(" hour", "h")
                .replace(" minutes", "m")
                .replace(" minute", "m")
                .replace(" seconds", "s");
	}
}
