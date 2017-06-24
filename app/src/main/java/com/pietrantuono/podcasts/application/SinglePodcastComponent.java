package com.pietrantuono.podcasts.application;

import com.pietrantuono.podcasts.player.player.service.PlayerService;
import com.pietrantuono.podcasts.singlepodcast.dagger.SinglePodcastModule;
import com.pietrantuono.podcasts.singlepodcast.view.SinglePodcastActivity;

import org.jetbrains.annotations.NotNull;

import dagger.Subcomponent;

@Subcomponent(modules = SinglePodcastModule.class)
public interface
SinglePodcastComponent {

    void inject(@NotNull SinglePodcastActivity singlePodcastActivity);

    void inject(@NotNull PlayerService playerService);
}
