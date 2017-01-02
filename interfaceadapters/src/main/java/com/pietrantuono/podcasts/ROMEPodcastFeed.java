package com.pietrantuono.podcasts;

import com.pietrantuono.CrashlyticsWrapper;
import com.pietrantuono.podcasts.apis.PodcastEpisode;
import com.pietrantuono.podcasts.apis.PodcastFeed;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;

import java.util.ArrayList;
import java.util.List;

public class ROMEPodcastFeed implements PodcastFeed {
    private final SyndFeed feed;
    private final List<PodcastEpisode> episodes;
    private final CrashlyticsWrapper crashlyticsWrapper;

    public ROMEPodcastFeed(SyndFeed feed, CrashlyticsWrapper crashlyticsWrapper) {
        this.feed = feed;
        this.crashlyticsWrapper = crashlyticsWrapper;
        episodes = parseEpisodes(feed);

    }

    @Override
    public List<PodcastEpisode> getEpisodes() {
        return episodes;
    }

    private List<PodcastEpisode> parseEpisodes(SyndFeed feed) {
        List<PodcastEpisode> podcastEpisodes = new ArrayList<>();
        for (SyndEntry entry : feed.getEntries()) {
            podcastEpisodes.add(new ROMEPodcastEpisode(entry, crashlyticsWrapper, context));
        }
        return podcastEpisodes;
    }
}
