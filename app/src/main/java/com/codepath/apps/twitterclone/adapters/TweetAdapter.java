package com.codepath.apps.twitterclone.adapters;

import androidx.paging.PagedListAdapter;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.codepath.apps.twitterclone.R;
import com.codepath.apps.twitterclone.models.Tweet;

public class TweetAdapter extends PagedListAdapter<Tweet, TweetAdapter.ViewHolder> {

    private Context context;

    public static final DiffUtil.ItemCallback<Tweet> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Tweet>() {
                @Override
                public boolean areItemsTheSame(Tweet oldItem, Tweet newItem) {
                    return oldItem.uid.equals(newItem.uid);
                }

                @Override
                public boolean areContentsTheSame(Tweet oldItem, Tweet newItem) {
                    return (oldItem.user.screenName.equals(newItem.user.screenName) &&
                            oldItem.body.equals(newItem.body));
                }
            };


    public TweetAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_tweet, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tweet tweet = getItem(position);
        if (tweet != null) {
            holder.tvTweet.setText(tweet.body);
            holder.tvName.setText(tweet.user.name);
            holder.tvHandle.setText(String.format("@%s", tweet.user.screenName));
            holder.tvCreated.setText(String.format("• %s", tweet.createdAt));
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

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivProfilePic;
        public TextView tvName;
        public TextView tvHandle;
        public TextView tvCreated;
        public TextView tvTweet;

        public ViewHolder(View itemView) {
            super(itemView);
            ivProfilePic = itemView.findViewById(R.id.ivProfilePic);
            tvName = itemView.findViewById(R.id.tvName);
            tvHandle = itemView.findViewById(R.id.tvHandle);
            tvCreated = itemView.findViewById(R.id.tvCreated);
            tvTweet = itemView.findViewById(R.id.tvTweet);
        }
    }
}
