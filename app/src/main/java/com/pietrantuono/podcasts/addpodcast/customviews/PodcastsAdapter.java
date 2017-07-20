package com.pietrantuono.podcasts.addpodcast.customviews;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pietrantuono.podcasts.R;
import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast;
import com.pietrantuono.podcasts.imageloader.SimpleImageLoader;
import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.ResourcesProvider;

import java.util.ArrayList;
import java.util.List;

public class PodcastsAdapter extends RecyclerView.Adapter<PodcastHolder> implements Filterable {
    private final List<SinglePodcast> items;
    private final List<SinglePodcast> publishedItems;
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


    public void onQueryTextChange(String newText) {
        getFilter().filter(newText);
    }

    @Override
    public Filter getFilter() {
        return new PodcastsFilter(this, items);
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
