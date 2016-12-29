package com.pietrantuono.podcasts.singlepodcast.customviews;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pietrantuono.podcasts.R;
import com.pietrantuono.podcasts.apis.PodcastEpisode;
import com.pietrantuono.podcasts.imageloader.SimpleImageLoader;

import java.util.ArrayList;
import java.util.List;

public class EpisodesAdapter extends RecyclerView.Adapter<EpisodeHolder> {
    private final List<PodcastEpisode> items;
    private final SimpleImageLoader simpleImageLoader;

    public EpisodesAdapter(SimpleImageLoader simpleImageLoader) {
        this.simpleImageLoader = simpleImageLoader;
        items = new ArrayList<>();
    }

    @Override
    public EpisodeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.episode_item, parent, false);
        EpisodeHolder holder = new EpisodeHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(EpisodeHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<PodcastEpisode> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }
}