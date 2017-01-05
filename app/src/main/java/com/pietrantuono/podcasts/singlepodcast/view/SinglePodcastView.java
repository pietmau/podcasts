package com.pietrantuono.podcasts.singlepodcast.view;

import com.pietrantuono.podcasts.apis.PodcastEpisodeModel;

import java.util.List;

public interface SinglePodcastView {

    void setEpisodes(List<PodcastEpisodeModel> episodes);
}
