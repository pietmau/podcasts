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

    public String getTrackCount() {
        return singlePodcast.getTrackCount() + " " + resourcesProvider.getString(R.string.episodes);
    }

    public String getReleaseDate() {
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

    public String getImageUrl(){
        return singlePodcast.getArtworkUrl600();
    }
}
