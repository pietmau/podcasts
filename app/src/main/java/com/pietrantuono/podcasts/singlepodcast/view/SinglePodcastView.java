package com.pietrantuono.podcasts.singlepodcast.view;

import com.pietrantuono.podcasts.apis.PodcastEpisode;

import java.util.List;

public interface SinglePodcastView {

    void setEpisodes(List<PodcastEpisode> episodes);
}
