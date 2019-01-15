package com.codepath.apps.twitterclone.ui.recView.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

import android.icu.text.SimpleDateFormat;
import android.text.format.DateUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Locale;

public class Tweet extends RealmObject {

    @PrimaryKey
	public Long uid;

    @Required
    public String createdAt;

	public User user;

    @Required
	public String body;

	public Tweet() {}

    public String getCreatedAt() {
        return getRelativeTimeAgo(createdAt);
    }

	public static Tweet fromJson(JSONObject object) throws JSONException{
		Tweet tweet = new Tweet();
		tweet.body = object.getString("text");
		tweet.uid = object.getLong("id");
		tweet.createdAt = object.getString("created_at");
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
		} catch (ParseException e) {e.printStackTrace();}

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
