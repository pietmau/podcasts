package com.pietrantuono.podcasts.subscribedpodcasts.detail.di;

import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.SingleSubscribedPodcastActivity;
import com.pietrantuono.podcasts.subscribedpodcasts.detail.views.SingleSubscribedPodcastsRecycler;

import org.jetbrains.annotations.NotNull;

import dagger.Subcomponent;

@SubscribedPodcastScope
@Subcomponent(modules = SingleSubscribedModule.class)
public interface SingleSubscribedComponent {

    void inject(@NotNull SingleSubscribedPodcastActivity singleSubscribedPodcastActivity);

    void inject(@NotNull SingleSubscribedPodcastsRecycler singleSubscribedPodcastsRecycler);
}
