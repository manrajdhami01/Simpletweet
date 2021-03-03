package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {

    public static final String TAG = "TweetsAdapter";

    Context context;
    List<Tweet> tweets;
    // Pass in the context and list of tweets


    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    // For each row, inflate the layout
    @NonNull
    @Override
    public TweetsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);
        return new ViewHolder(view);
    }

    // Bind value based on the position of the element
    @Override
    public void onBindViewHolder(@NonNull TweetsAdapter.ViewHolder holder, int position) {
        // Get data at position
        Tweet tweet = tweets.get(position);
        //Bind the tweet with view holder
        holder.bind(tweet);
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        tweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items
    public void addAll(List<Tweet> tweetList) {
        tweets.addAll(tweetList);
        notifyDataSetChanged();
    }


    // Define a viewholder
    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivProfileImage;
        TextView tvBody;
        TextView tvScreenName;
        TextView tvName;
        TextView tvCreatedAt;
        TextView tvRetweets;
        TextView tvFavorites;
        ImageView ivEmbeded;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            tvName = itemView.findViewById(R.id.tvName);
            tvCreatedAt = itemView.findViewById(R.id.tvCreatedAt);
            tvFavorites = itemView.findViewById(R.id.tvFavorites);
            tvRetweets = itemView.findViewById(R.id.tvRetweets);
            ivEmbeded = itemView.findViewById(R.id.ivEmbeded);

        }

        public void bind(Tweet tweet) {
            tvBody.setText(tweet.body);
            tvScreenName.setText(tweet.user.screenName);
            Glide.with(context).load(tweet.user.publicImageUrl).into(ivProfileImage);
            tvName.setText(tweet.user.name);
            tvCreatedAt.setText(getRelativeTimeAgo(tweet.createdAt));
            tvRetweets.setText(tweet.retweetCount);
            tvFavorites.setText(tweet.favoriteCount);
            Glide.with(context).load(tweet.embeddedImage).into(ivEmbeded);
        }
    }

    private void loadImage(Tweet tweet, ImageView img) {
        if (!tweet.embeddedImage.isEmpty()) {
            Log.i(TAG, "Embeding image" + tweet.embeddedImage);
            Glide.with(context).load(tweet.embeddedImage).into(img);
        } else {
            return;
        }
    }


        public String getRelativeTimeAgo (String rawJsonDate){
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

            return relativeDate;
        }
    }
