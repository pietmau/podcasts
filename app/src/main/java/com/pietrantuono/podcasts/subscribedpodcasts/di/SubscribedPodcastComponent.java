package com.pietrantuono.podcasts.subscribedpodcasts.di;

import com.pietrantuono.podcasts.subscribedpodcasts.view.SubscribedPodcastFragment;

import dagger.Subcomponent;

@Subcomponent(modules = SubscribedPodcastModule.class)
public interface SubscribedPodcastComponent {

    void inject(SubscribedPodcastFragment subscribedPodcastFragment);
}
