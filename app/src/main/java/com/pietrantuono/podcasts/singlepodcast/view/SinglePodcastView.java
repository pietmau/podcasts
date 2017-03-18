package com.pietrantuono.podcasts.singlepodcast.view;

import com.pietrantuono.podcasts.apis.PodcastEpisodeModel;

import java.util.List;

public interface SinglePodcastView {

    void enterWithTransition();

    void enterWithoutTransition();

    void showProgress(boolean show);

    void setEpisodes(List<PodcastEpisodeModel> episodes);

    void exitWithSharedTrsnsition();

    void exitWithoutSharedTransition();

    void setSubscribed(Boolean isSubscribed);
}
