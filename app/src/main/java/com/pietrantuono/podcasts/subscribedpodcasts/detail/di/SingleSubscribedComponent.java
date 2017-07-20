package com.pietrantuono.podcasts.subscribedpodcasts.detail.di;

import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.SingleSubscribedPodcastActivity;

import org.jetbrains.annotations.NotNull;

import dagger.Subcomponent;

@Subcomponent(modules = SingleSubscribedModule.class)
public interface SingleSubscribedComponent {

    void inject(@NotNull SingleSubscribedPodcastActivity singleSubscribedPodcastActivity) ,
}
