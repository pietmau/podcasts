package com.pietrantuono.podcasts;

import com.pietrantuono.podcasts.apis.PodcastFeed;
import com.rometools.rome.feed.synd.SyndFeed;

public class ROMEPodcastFeed implements PodcastFeed {
    private SyndFeed feed;

    public ROMEPodcastFeed(SyndFeed feed) {
        this.feed = feed;
    }
}
