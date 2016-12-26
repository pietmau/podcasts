package com.pietrantuono.podcasts.addpodcast.customviews;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.pietrantuono.podcasts.R;
import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast;

import java.util.ArrayList;
import java.util.List;

public class PodcastsAdapter extends RecyclerView.Adapter<PodcastHolder> implements Filterable {
    private List<SinglePodcast> items;
    private List<SinglePodcast> publishedItems;
    private ImageLoader imageLoader;
    private OnSunscribeClickedListener onSunscribeClickedListener;
    private OnItemClickedClickedListener onItemClickedClickedListener;

    public PodcastsAdapter(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
        items = new ArrayList<>();
        publishedItems = new ArrayList<>();
    }

    @Override
    public PodcastHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.podcast_item, parent, false);
        return new PodcastHolder(v, imageLoader);
    }

    @Override
    public void onBindViewHolder(PodcastHolder holder, int position) {
        holder.onBindViewHolder(publishedItems.get(position), onSunscribeClickedListener, onItemClickedClickedListener);
    }

    @Override
    public int getItemCount() {
        return publishedItems.size();
    }

    public void setItems(List<SinglePodcast> items) {
        prefetch(items);
        this.items.clear();
        this.items.addAll(items);
        setPublishedItems(items);
    }

    public void setPublishedItems(List<SinglePodcast> items){
        publishedItems.clear();
        publishedItems.addAll(items);
        notifyDataSetChanged();
    }

    private void prefetch(List<SinglePodcast> items) {
        for (SinglePodcast item : items) {
            imageLoader.loadImage(item.getArtworkUrl600(), null);
        }
    }

    public void onQueryTextChange(String newText) {
        getFilter().filter(newText);
    }

    @Override
    public Filter getFilter() {
        return new PodcastsFilter(this, items);
    }

    public void setOnSubscribeListener(OnSunscribeClickedListener onSunscribeClickedListener) {
        this.onSunscribeClickedListener = onSunscribeClickedListener;
    }

    public void setOnItemClickListener(OnItemClickedClickedListener onItemClickedClickedListener) {
        this.onItemClickedClickedListener = onItemClickedClickedListener;
    }

    public interface OnSunscribeClickedListener {
        void onSubscribeClicked(SinglePodcast singlePodcast);
    }

    public interface OnItemClickedClickedListener {
        void onItemClicked(SinglePodcast singlePodcast, ImageView imageView);
    }
}
