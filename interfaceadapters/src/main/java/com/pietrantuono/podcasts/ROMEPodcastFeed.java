package com.pietrantuono.podcasts;

import com.pietrantuono.podcasts.apis.PodcastEpisode;
import com.pietrantuono.podcasts.apis.PodcastFeed;
import com.rometools.rome.feed.synd.SyndFeed;

import java.util.List;

public class ROMEPodcastFeed implements PodcastFeed {
    private SyndFeed feed;

    public ROMEPodcastFeed(SyndFeed feed) {
        this.feed = feed;
    }

    @Override
    public List<PodcastEpisode> getEpisodes() {
        return null;
    }
}
