package com.pietrantuono.podcasts.interfaces;

import com.pietrantuono.podcasts.apis.PodcastEpisodeModel;
import com.pietrantuono.podcasts.apis.PodcastFeed;

import java.util.List;

public class ROMEPodcastFeed implements PodcastFeed {
    private final List<PodcastEpisodeModel> episodes;

    public ROMEPodcastFeed(List<PodcastEpisodeModel> episodes) {
        this.episodes = episodes;
    }

    @Override
    public List<PodcastEpisodeModel> getEpisodes() {
        return episodes;
    }

}
