package com.pietrantuono.podcasts.interfaces;

import com.pietrantuono.podcasts.apis.Episode;
import com.pietrantuono.podcasts.apis.PodcastFeed;

import java.util.List;

public class ROMEPodcastFeed implements PodcastFeed {
    private final List<Episode> episodes;

    public ROMEPodcastFeed(List<Episode> episodes) {
        this.episodes = episodes;
    }

    @Override
    public List<Episode> getEpisodes() {
        return episodes;
    }

}
