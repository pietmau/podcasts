package com.pietrantuono.podcasts.subscribedpodcasts.detail;

import com.pietrantuono.podcasts.apis.PodcastEpisode;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface SinglePodcastView {

    void enterWithTransition();

    void enterWithoutTransition();

    void showProgress(boolean show);

    void setEpisodes(@Nullable List<PodcastEpisode> episodes);

    void exitWithSharedTrsnsition();

    void exitWithoutSharedTransition();

    void setSubscribedToPodcast(Boolean isSubscribed);

    void setTitle(@Nullable String collectionName);
}
