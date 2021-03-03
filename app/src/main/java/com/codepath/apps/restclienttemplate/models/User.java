package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {

    public String name;
    public String screenName;
    public String publicImageUrl;


    public static User fromJson(JSONObject JsonObject) throws JSONException {
        User user = new User();
        user.name = JsonObject.getString("name");
        user.screenName = "@" + JsonObject.getString("screen_name");
        user.publicImageUrl = JsonObject.getString("profile_image_url_https");
        return user;
    }
}

