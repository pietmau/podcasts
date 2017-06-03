package com.pietrantuono.podcasts.singlepodcast.view;

import com.pietrantuono.podcasts.apis.PodcastEpisodeModel;
import com.pietrantuono.podcasts.apis.PodcastFeed;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface SinglePodcastView {

    void enterWithTransition();

    void enterWithoutTransition();

    void showProgress(boolean show);

    void setEpisodes(@Nullable List<PodcastEpisodeModel> episodes);

    void exitWithSharedTrsnsition();

    void exitWithoutSharedTransition();

    void setSubscribedToPodcast(Boolean isSubscribed);

    void listenToAll(@Nullable PodcastFeed podcastFeed);
}
