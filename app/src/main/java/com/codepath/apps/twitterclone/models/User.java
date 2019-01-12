package com.codepath.apps.twitterclone.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {

    public String name;
    public long uid;
    public String screenName;
    public String profilePicUrl;
    public String profilePicUrl_HD;

    public static User fromJson(JSONObject object) throws JSONException {
        User user = new User();
        user.name = object.getString("name");
        user.uid = object.getLong("id");
        user.screenName = object.getString("screen_name");
        user.profilePicUrl = object.getString("profile_image_url");
        user.profilePicUrl_HD = user.profilePicUrl.replace("_normal", "");
        return user;
    }
}
