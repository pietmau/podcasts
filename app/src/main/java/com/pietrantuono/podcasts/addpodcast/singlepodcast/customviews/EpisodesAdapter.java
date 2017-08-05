package com.pietrantuono.podcasts.addpodcast.singlepodcast.customviews;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pietrantuono.podcasts.R;
import com.pietrantuono.podcasts.apis.PodcastEpisode;
import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.ResourcesProvider;

import java.util.ArrayList;
import java.util.List;

public class EpisodesAdapter extends RecyclerView.Adapter<EpisodeHolder> {
    private final List<PodcastEpisode> items;
    private final ResourcesProvider resourcesProvider;

    public EpisodesAdapter(ResourcesProvider resourcesProvider) {
        this.resourcesProvider = resourcesProvider;
        items = new ArrayList<>();
    }

    @Override
    public EpisodeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.episode_item, parent, false);
        return new EpisodeHolder(view, resourcesProvider);
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
