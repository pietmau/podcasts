package com.pietrantuono.podcasts.addpodcast.customviews;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pietrantuono.podcasts.R;
import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.ResourcesProvider;
import com.pietrantuono.podcasts.imageloader.SimpleImageLoader;

import java.util.ArrayList;
import java.util.List;

import diocan.pojos.Podcast;

public class PodcastsAdapter extends RecyclerView.Adapter<PodcastHolder> {
    private final List<Podcast> items;
    private final List<Podcast> publishedItems;
    private final SimpleImageLoader imageLoader;
    @NonNull private OnItemClickedClickedListener onItemClickedClickedListener;
    @NonNull private ResourcesProvider resolver;

    public PodcastsAdapter(@NonNull SimpleImageLoader imageLoader, @NonNull ResourcesProvider resolver) {
        this.imageLoader = imageLoader;
        this.resolver = resolver;
        items = new ArrayList<>();
        publishedItems = new ArrayList<>();
    }

    @Override
    public PodcastHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.find_podcast_item, parent, false);
        return new PodcastHolder(view, resolver, imageLoader);
    }

    @Override
    public void onBindViewHolder(@NonNull PodcastHolder holder, int position) {
        holder.onBindViewHolder(publishedItems.get(position), onItemClickedClickedListener, position);
    }

    @Override
    public int getItemCount() {
        return publishedItems.size();
    }

    public void setItems(List<Podcast> items) {
        this.items.clear();
        this.items.addAll(items);
        setPublishedItems(items);
    }

    public void setPublishedItems(List<Podcast> items) {
        publishedItems.clear();
        publishedItems.addAll(items);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(@NonNull OnItemClickedClickedListener onItemClickedClickedListener) {
        this.onItemClickedClickedListener = onItemClickedClickedListener;
    }

    public interface OnSunscribeClickedListener {
        void onSubscribeClicked(Podcast podcast);
    }

    public interface OnItemClickedClickedListener {
        void onItemClicked(Podcast podcast, ImageView imageView, int position, LinearLayout titleContainer);
    }
}
