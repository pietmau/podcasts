package com.pietrantuono.podcasts.subscribedpodcasts.detail.views;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pietrantuono.podcasts.R;
import com.pietrantuono.podcasts.addpodcast.customviews.SingleSubscribedPodcastHolder;
import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.ResourcesProvider;
import com.pietrantuono.podcasts.apis.PodcastEpisodeModel;

import java.util.ArrayList;
import java.util.List;

public class SingleSubscribedPodcastsAdapter extends RecyclerView.Adapter<SingleSubscribedPodcastHolder> {
    private final List<PodcastEpisodeModel> items;
    private final ResourcesProvider resourcesProvider;

    public SingleSubscribedPodcastsAdapter(ResourcesProvider resourcesProvider) {
        this.resourcesProvider = resourcesProvider;
        items = new ArrayList<>();
    }

    @Override
    public SingleSubscribedPodcastHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.episode_item, parent, false);
        return new SingleSubscribedPodcastHolder(view, resourcesProvider);
    }

    @Override
    public void onBindViewHolder(SingleSubscribedPodcastHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<PodcastEpisodeModel> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }
}
