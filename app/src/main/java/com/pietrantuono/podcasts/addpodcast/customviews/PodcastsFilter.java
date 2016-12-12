package com.pietrantuono.podcasts.addpodcast.customviews;

import android.widget.Filter;

import com.pietrantuono.podcasts.addpodcast.model.pojos.PodcastSearchResult;

import java.util.ArrayList;
import java.util.List;

class PodcastsFilter extends Filter {
    private PodcastsAdapter podcastsAdapter;
    private List<PodcastSearchResult> items;

    public PodcastsFilter(PodcastsAdapter podcastsAdapter, List<PodcastSearchResult> items) {
        this.podcastsAdapter = podcastsAdapter;
        this.items = items;
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
        List<PodcastSearchResult> results = new ArrayList<>();
        for (PodcastSearchResult item : items) {
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
        List<PodcastSearchResult> publishedItems = (List<PodcastSearchResult>) filterResults.values;
        podcastsAdapter.setPublishedItems(publishedItems);
        podcastsAdapter.notifyDataSetChanged();
    }
}
