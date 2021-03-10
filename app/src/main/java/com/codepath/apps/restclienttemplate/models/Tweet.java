package com.codepath.apps.restclienttemplate.models;

import android.text.format.DateUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.TimeZone;

@Parcel
@Entity(foreignKeys = @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "userId"))
public class Tweet {

    public static final String TAG = "TweetAct";

    @ColumnInfo
    @PrimaryKey
    public long id;

    @ColumnInfo
    public String body;

    @ColumnInfo
    public String createdAt;

    @ColumnInfo
    public long userId;

    @Ignore
    public User user;

    @ColumnInfo
    public String retweetCount;

    @ColumnInfo
    public String favoriteCount;

    @ColumnInfo
    public String embeddedImage;

    // Empty constructor needed by the Parceler library
    public Tweet() {}


    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.body = jsonObject.getString("text");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.id = jsonObject.getLong("id");
        User user = User.fromJson(jsonObject.getJSONObject("user"));
        tweet.user = user;
        tweet.retweetCount = String.valueOf(jsonObject.getString("retweet_count"));
        tweet.favoriteCount = String.valueOf(jsonObject.getString("favorite_count"));
        tweet.userId = user.id;
        if (jsonObject.getJSONObject("entities").has("media")){
            Log.i(TAG, "the picture link: " + tweet.user);
            tweet.embeddedImage = jsonObject.getJSONObject("entities").getJSONArray("media").getJSONObject(0).getString("media_url_https").substring(0, (jsonObject.getJSONObject("entities").getJSONArray("media").getJSONObject(0).getString("media_url_https")).length()-4) + "?format=jpg&name=large";
        }
        else {
            tweet.embeddedImage = "";
        }
        return tweet;
    }


    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets = new ArrayList<>();
        for (int i=0; i<jsonArray.length(); i++) {
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return tweets;
    }

}
