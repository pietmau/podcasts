package com.pietrantuono.podcasts.addpodcast.singlepodcast.view;

import com.pietrantuono.podcasts.apis.Episode;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface SinglePodcastView {

    void enterWithTransition();

    void enterWithoutTransition();

    void showProgress(boolean show);

    void setEpisodes(@Nullable List<Episode> episodes);

    void exitWithSharedTrsnsition();

    void exitWithoutSharedTransition();

    void setSubscribedToPodcast(Boolean isSubscribed);

    void setTitle(@Nullable String collectionName);
}
