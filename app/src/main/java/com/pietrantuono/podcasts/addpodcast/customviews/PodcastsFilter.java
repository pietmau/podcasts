package com.pietrantuono.podcasts.addpodcast.customviews;

import android.widget.Filter;

import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast;

import java.util.ArrayList;
import java.util.List;

class PodcastsFilter extends Filter {
    private PodcastsAdapter podcastsAdapter;
    private List<SinglePodcast> items;

    public PodcastsFilter(PodcastsAdapter podcastsAdapter, List<SinglePodcast> items) {
        this.podcastsAdapter = podcastsAdapter;
        this.items = items;
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
        List<SinglePodcast> results = new ArrayList<>();
        for (SinglePodcast item : items) {
            if (item.getCollectionName() != null && item.getCollectionName().toLowerCase().contains(charSequence)) {
                results.add(item);
            }
        }
        FilterResults filterResults = new FilterResults();
        filterResults.values = results;
        return filterResults;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        List<SinglePodcast> publishedItems = (List<SinglePodcast>) filterResults.values;
        podcastsAdapter.setPublishedItems(publishedItems);
        podcastsAdapter.notifyDataSetChanged();
    }
}
