package com.pietrantuono.podcasts.subscribedpodcasts.detail.views;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pietrantuono.podcasts.R;
import com.pietrantuono.podcasts.addpodcast.customviews.SingleSubscribedPodcastHolder;
import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast;
import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.ResourcesProvider;
import com.pietrantuono.podcasts.imageloader.SimpleImageLoader;

import java.util.ArrayList;
import java.util.List;

public class SingleSubscribedPodcastsAdapter extends RecyclerView.Adapter<SingleSubscribedPodcastHolder> {
    private final List<SinglePodcast> items;
    private final List<SinglePodcast> publishedItems;
    private final SimpleImageLoader imageLoader;
    @NonNull private OnItemClickedClickedListener onItemClickedClickedListener;
    @NonNull private ResourcesProvider resolver;

    public SingleSubscribedPodcastsAdapter(@NonNull SimpleImageLoader imageLoader, @NonNull ResourcesProvider resolver) {
        this.imageLoader = imageLoader;
        this.resolver = resolver;
        items = new ArrayList<>();
        publishedItems = new ArrayList<>();
    }

    @Override
    public SingleSubscribedPodcastHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.find_podcast_item, parent, false);
        return new SingleSubscribedPodcastHolder(view, resolver, imageLoader);
    }

    @Override
    public void onBindViewHolder(@NonNull SingleSubscribedPodcastHolder holder, int position) {
        holder.onBindViewHolder(publishedItems.get(position), onItemClickedClickedListener, position);
    }

    @Override
    public int getItemCount() {
        return publishedItems.size();
    }

    public void setItems(List<SinglePodcast> items) {
        this.items.clear();
        this.items.addAll(items);
        setPublishedItems(items);
    }

    public void setPublishedItems(List<SinglePodcast> items) {
        publishedItems.clear();
        publishedItems.addAll(items);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(@NonNull OnItemClickedClickedListener onItemClickedClickedListener) {
        this.onItemClickedClickedListener = onItemClickedClickedListener;
    }

    public interface OnSunscribeClickedListener {
        void onSubscribeClicked(SinglePodcast singlePodcast);
    }

    public interface OnItemClickedClickedListener {
        void onItemClicked(SinglePodcast singlePodcast, ImageView imageView, int position, LinearLayout titleContainer);
    }
}
