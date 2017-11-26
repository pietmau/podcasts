package com.pietrantuono.podcasts.addpodcast.customviews;


import com.pietrantuono.podcasts.R;
import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.ResourcesProvider;

import java.text.SimpleDateFormat;
import java.util.Date;

import diocan.pojos.Podcast;

public class SinlglePodcastViewModel {
    private ResourcesProvider resourcesProvider;
    private final Podcast podcast;

    public SinlglePodcastViewModel(Podcast podcast, ResourcesProvider resourcesProvider) {
        this.resourcesProvider = resourcesProvider;
        this.podcast = podcast;
    }

    public String getTitle() {
        return podcast.getTrackName();
    }

    public String getAuthor() {
        return podcast.getArtistName();
    }

    public String getGenre() {
        return podcast.getPrimaryGenreName();
    }

    public String getTrackCount() {
        return podcast.getTrackCount() + " " + resourcesProvider.getString(R.string.episodes);
    }

    public String getReleaseDate() {
        SimpleDateFormat in = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
        Date date = null;
        try {
            date = in.parse(podcast.getReleaseDate());
        } catch (Exception e) {
            return null;
        }
        SimpleDateFormat out = new SimpleDateFormat("MMMM yyyy");
        return out.format(date);
    }

    public String getImageUrl(){
        return podcast.getArtworkUrl600();
    }
}
