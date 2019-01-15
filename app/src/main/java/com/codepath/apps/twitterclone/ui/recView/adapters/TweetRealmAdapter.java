package com.codepath.apps.twitterclone.ui.recView.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.codepath.apps.twitterclone.R;
import com.codepath.apps.twitterclone.api.TweetRepository;
import com.codepath.apps.twitterclone.ui.recView.models.Tweet;
import com.codepath.apps.twitterclone.ui.recView.util.TweetViewHolder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

public class TweetRealmAdapter extends RealmRecyclerViewAdapter<Tweet, TweetViewHolder> {
    private Context context;


    public TweetRealmAdapter(Context context, @Nullable OrderedRealmCollection<Tweet> data, boolean autoUpdate) {
        super(data, autoUpdate);
        this.context = context;
    }

    @NonNull
    @Override
    public TweetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TweetViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_tweet, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull TweetViewHolder holder, int position) {
        Tweet tweet = getItem(position);
        if (tweet != null) {
            holder.tvTweet.setText(tweet.body);
            holder.tvName.setText(tweet.user.name);
            holder.tvHandle.setText(String.format("@%s", tweet.user.screenName));
            holder.tvCreated.setText(String.format("• %s", tweet.getCreatedAt()));
            Glide.with(context).load(tweet.user.profilePicUrl_HD)
                    .apply(new RequestOptions().transform(new CircleCrop()))
                    .thumbnail(Glide.with(context).load(tweet.user.profilePicUrl)
                            .apply(new RequestOptions().transform(new CircleCrop())))
                    .into(holder.ivProfilePic);
        }
        else {
            holder.tvTweet.setText(R.string.str_loading);
            holder.tvName.setText(R.string.str_loading);
            holder.ivProfilePic.setImageResource(0);
        }
    }
}
