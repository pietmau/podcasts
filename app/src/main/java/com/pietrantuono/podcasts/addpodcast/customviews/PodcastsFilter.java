package com.pietrantuono.podcasts.addpodcast.customviews;

import android.widget.Filter;
import java.util.ArrayList;
import java.util.List;

import pojos.Podcast;

class PodcastsFilter extends Filter {
    private final PodcastsAdapter podcastsAdapter;
    private final List<Podcast> items;

    public PodcastsFilter(PodcastsAdapter podcastsAdapter, List<Podcast> items) {
        this.podcastsAdapter = podcastsAdapter;
        this.items = items;
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
        List<Podcast> results = new ArrayList<>();
        for (Podcast item : items) {
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
        List<Podcast> publishedItems = (List<Podcast>) filterResults.values;
        podcastsAdapter.setPublishedItems(publishedItems);
        podcastsAdapter.notifyDataSetChanged();
    }
}
