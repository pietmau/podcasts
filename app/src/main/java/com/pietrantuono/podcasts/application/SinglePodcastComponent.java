package com.pietrantuono.podcasts.application;

import com.pietrantuono.podcasts.addpodcast.singlepodcast.dagger.SinglePodcastModule;
import com.pietrantuono.podcasts.addpodcast.singlepodcast.dagger.SinglePodcastScope;
import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.AddSinglePodcastActivity;

import org.jetbrains.annotations.NotNull;

import dagger.Subcomponent;

@SinglePodcastScope
@Subcomponent(modules = SinglePodcastModule.class)
public interface
SinglePodcastComponent {

    void inject(@NotNull AddSinglePodcastActivity singlePodcastActivity);

}
