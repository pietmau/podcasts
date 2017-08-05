package com.pietrantuono.podcasts.interfaces;

import com.pietrantuono.podcasts.apis.PodcastEpisode;
import com.pietrantuono.podcasts.apis.PodcastFeed;

import java.util.List;

public class ROMEPodcastFeed implements PodcastFeed {
    private final List<PodcastEpisode> episodes;

    public ROMEPodcastFeed(List<PodcastEpisode> episodes) {
        this.episodes = episodes;
    }

    @Override
    public List<PodcastEpisode> getEpisodes() {
        return episodes;
    }

}
