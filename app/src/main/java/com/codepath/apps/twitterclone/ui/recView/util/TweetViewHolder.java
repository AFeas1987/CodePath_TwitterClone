package com.codepath.apps.twitterclone.ui.recView.util;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.twitterclone.R;


public class TweetViewHolder extends RecyclerView.ViewHolder {

    public ImageView ivProfilePic;
    public TextView tvName;
    public TextView tvHandle;
    public TextView tvCreated;
    public TextView tvTweet;

    public TweetViewHolder(View itemView) {
        super(itemView);
        ivProfilePic = itemView.findViewById(R.id.ivProfilePic);
        tvName = itemView.findViewById(R.id.tvName);
        tvHandle = itemView.findViewById(R.id.tvHandle);
        tvCreated = itemView.findViewById(R.id.tvCreated);
        tvTweet = itemView.findViewById(R.id.tvTweet);
    }
}
