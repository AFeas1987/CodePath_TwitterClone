package com.codepath.apps.twitterclone.ui.recView.models;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class User extends RealmObject {

    @PrimaryKey
    public long uid;

    @Required
    public String name;

    @Required
    public String screenName;

    @Required
    public String profilePicUrl;

    @Required
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
