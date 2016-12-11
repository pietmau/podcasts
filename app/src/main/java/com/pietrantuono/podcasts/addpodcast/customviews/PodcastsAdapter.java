package com.pietrantuono.podcasts.addpodcast.customviews;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pietrantuono.podcasts.R;
import com.pietrantuono.podcasts.addpodcast.model.pojos.PodcastSearchResult;

import java.util.ArrayList;
import java.util.List;

public class PodcastsAdapter extends RecyclerView.Adapter<PodcastHolder> {
    private List<PodcastSearchResult> items;

    public PodcastsAdapter() {
        items = new ArrayList<>();
    }

    @Override
    public PodcastHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.podcast_item, parent, false);
        return new PodcastHolder(v);
    }

    @Override
    public void onBindViewHolder(PodcastHolder holder, int position) {
        holder.onBindViewHolder(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<PodcastSearchResult> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }
}
