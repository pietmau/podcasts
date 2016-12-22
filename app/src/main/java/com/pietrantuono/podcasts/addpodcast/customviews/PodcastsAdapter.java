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
import com.pietrantuono.podcasts.addpodcast.model.pojos.PodcastSearchResult;

import java.util.ArrayList;
import java.util.List;

public class PodcastsAdapter extends RecyclerView.Adapter<PodcastHolder> implements Filterable {
    private List<PodcastSearchResult> items;
    private List<PodcastSearchResult> publishedItems;
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

    public void setItems(List<PodcastSearchResult> items) {
        prefetch(items);
        this.items.clear();
        this.items.addAll(items);
        setPublishedItems(items);
    }

    public void setPublishedItems(List<PodcastSearchResult> items){
        publishedItems.clear();
        publishedItems.addAll(items);
        notifyDataSetChanged();
    }

    private void prefetch(List<PodcastSearchResult> items) {
        for (PodcastSearchResult item : items) {
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
        void onSunscribeClicked(PodcastSearchResult podcastSearchResult);
    }

    public interface OnItemClickedClickedListener {
        void onItemClicked(PodcastSearchResult podcastSearchResult, ImageView imageView);
    }
}
