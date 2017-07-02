package com.pietrantuono.podcasts;

import com.pietrantuono.interfaceadapters.apis.PodcastEpisodeModel;
import com.pietrantuono.interfaceadapters.apis.PodcastFeed;

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
