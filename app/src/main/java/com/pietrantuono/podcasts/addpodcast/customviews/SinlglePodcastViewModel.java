package com.pietrantuono.podcasts.addpodcast.customviews;


import com.pietrantuono.podcasts.R;
import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast;
import com.pietrantuono.podcasts.singlepodcast.viewmodel.ResourcesProvider;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SinlglePodcastViewModel {
    private ResourcesProvider resourcesProvider;
    private final PodcastsAdapter.OnSunscribeClickedListener onSunscribeClickedListener;
    private final PodcastsAdapter.OnItemClickedClickedListener onItemClickedClickedListener;
    private final SinglePodcast singlePodcast;
    private final int position;

    public SinlglePodcastViewModel(SinglePodcast singlePodcast, ResourcesProvider resourcesProvider, PodcastsAdapter.OnItemClickedClickedListener onItemClickedClickedListener, PodcastsAdapter.OnSunscribeClickedListener onSunscribeClickedListener, int position) {
        this.resourcesProvider = resourcesProvider;
        this.onSunscribeClickedListener = onSunscribeClickedListener;
        this.onItemClickedClickedListener = onItemClickedClickedListener;
        this.singlePodcast = singlePodcast;
        this.position = position;
    }

    public String getTitle() {
        return singlePodcast.getTrackName();
    }

    public String getAuthor() {
        return singlePodcast.getArtistName();
    }

    public String getGenre() {
        return singlePodcast.getPrimaryGenreName();
    }

    private String getTrackCount(SinglePodcast singlePodcast) {
        return singlePodcast.getTrackCount() + " " + resourcesProvider.getString(R.string.episodes);
    }

    private String getReleaseDate() {
        SimpleDateFormat in = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
        Date date = null;
        try {
            date = in.parse(singlePodcast.getReleaseDate());
        } catch (Exception e) {
            return null;
        }
        SimpleDateFormat out = new SimpleDateFormat("MMMM yyyy");
        return out.format(date);
    }

    private void onItemClicked() {
        onItemClickedClickedListener.onItemClicked(singlePodcast, imageView, position);
    }
}
