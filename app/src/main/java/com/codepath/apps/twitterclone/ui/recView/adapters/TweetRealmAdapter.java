package com.codepath.apps.twitterclone.ui.recView.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.codepath.apps.twitterclone.R;
import com.codepath.apps.twitterclone.api.TwitterApplication;
import com.codepath.apps.twitterclone.ui.recView.models.Tweet;
import com.codepath.apps.twitterclone.ui.recView.util.TweetViewHolder;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

import static com.codepath.apps.twitterclone.api.network.TwitterClient.PAGE_SIZE;

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

    public SwipeRefreshLayout.OnScrollChangeListener createScrollListener(RecyclerView rView){
        return (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            LinearLayoutManager llMgr = (LinearLayoutManager)rView.getLayoutManager();
            int current = llMgr != null ? llMgr.findLastVisibleItemPosition() : 0;
            int count = getItemCount();
            if (current % PAGE_SIZE == 0 || count - PAGE_SIZE <= current) {     // Should first check for idle network
                Log.d("_AF", "loadOlder requested");
//                int pos = current + PAGE_SIZE < count ?
//                        current + PAGE_SIZE : count;
//                Tweet t = getItem(pos);
//                if (t != null)
//                    TwitterApplication.getTweetRepo().loadOlderTweets(t.uid);
            }
        };
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

    public void refreshVisibleData(RecyclerView rView) {
        LinearLayoutManager llMgr = (LinearLayoutManager)rView.getLayoutManager();
        int first = llMgr.findFirstVisibleItemPosition();
        int last = llMgr.findLastVisibleItemPosition();
        notifyItemRangeChanged(first, last - first);
    }
}
