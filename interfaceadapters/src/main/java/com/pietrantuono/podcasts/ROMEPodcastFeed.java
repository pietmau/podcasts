package com.pietrantuono.podcasts;

import com.pietrantuono.podcasts.apis.PodcastEpisode;
import com.pietrantuono.podcasts.apis.PodcastFeed;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;

import java.util.ArrayList;
import java.util.List;

public class ROMEPodcastFeed implements PodcastFeed {
    private final SyndFeed feed;
    private final List<PodcastEpisode> episodes;

    public ROMEPodcastFeed(SyndFeed feed) {
        this.feed = feed;
        episodes = parseEpisodes(feed);
    }

    @Override
    public List<PodcastEpisode> getEpisodes() {
        return episodes;
    }


    private List<PodcastEpisode> parseEpisodes(SyndFeed feed) {
        List<PodcastEpisode> podcastEpisodes = new ArrayList<>();
        for (SyndEntry entry : feed.getEntries()) {
            podcastEpisodes.add(new ROMEPodcastEpisode(entry));
        }
        return podcastEpisodes;
    }
}
